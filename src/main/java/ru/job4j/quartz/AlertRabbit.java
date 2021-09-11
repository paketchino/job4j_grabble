package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import javax.naming.Context;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Properties;

public class AlertRabbit {

    private Connection connection;

    private Properties properties;

    public AlertRabbit(Properties properties) throws ClassNotFoundException {
        this.properties = properties;
        init();
    }

    private void getConnection(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(String tableName) {
        String sql = String.format("create table if not exists %s()", tableName);
        getConnection(sql);
    }

    private void init() throws ClassNotFoundException {
        Class.forName("driver-class-name");
        ClassLoader classLoader = AlertRabbit.class.getClassLoader();
        try (InputStream loader = classLoader.getResourceAsStream("rabbit.properties")) {
            properties.load(loader);
            connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("login"),
                    properties.getProperty("password")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int readFileProperties() {
        Properties properties = new Properties();
        ClassLoader classLoader = AlertRabbit.class.getClassLoader();
        try (InputStream loader = classLoader.getResourceAsStream("rabbit.properties")) {
            properties.load(loader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(properties.getProperty("rabbit.interval"));
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Properties properties = new Properties();
        AlertRabbit alertRabbit = new AlertRabbit(properties);
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap jobDataMap = new JobDataMap();
            //jobDataMap.put(alertRabbit.createTable("data"), jobDataMap);
            JobDetail jobDetail = newJob(Rabbit.class)
                    .usingJobData(jobDataMap).build();
            SimpleScheduleBuilder times = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(AlertRabbit.readFileProperties())
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            Thread.sleep(5000);
            scheduler.shutdown();
            System.out.println();
        } catch (SchedulerException | InterruptedException se) {
            se.printStackTrace();
        }
    }


    public static class Rabbit implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection cn = (Connection) (jobExecutionContext.getJobDetail().getJobDataMap().get("connection"));
            try (PreparedStatement statement = cn.prepareStatement("")) {
                statement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
