package ru.job4j;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import org.quartz.Scheduler;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class AlertRabbit {

    private Connection connection;

    private Properties properties;

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        ClassLoader classLoader = AlertRabbit.class.getClassLoader();
        try (InputStream loader = classLoader.getResourceAsStream("rabbit.properties")) {
            properties.load(loader);
        }
        int value = Integer.parseInt(properties.getProperty("rabbit.interval"));
        Class.forName(properties.getProperty("driver-class-name"));
        try (Connection connection = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("login"),
                properties.getProperty("password")
        )) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("connection", connection);
            JobDetail jobDetail = newJob(Rabbit.class)
                    .usingJobData(jobDataMap).build();
            SimpleScheduleBuilder times = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(value)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
            System.out.println();
        }
    }


    public static class Rabbit implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection cn = (Connection) (jobExecutionContext.getJobDetail().getJobDataMap().get("connection"));
            try (PreparedStatement statement =
                         cn.prepareStatement(
                    "insert into rabbit1 (created_date) values (current_timestamp)"
                         )) {
                statement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
