package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.io.InputStream;
import java.util.Properties;

public class AlertRabbit {

    private Properties properties;

    public AlertRabbit(Properties properties) {
        this.properties = properties;
    }

    public static int readFileProperties() {
        Properties properties = new Properties();
        ClassLoader classLoader = Rabbit.class.getClassLoader();
        try (InputStream loader = classLoader.getResourceAsStream("rabbit.properties")) {
            properties.load(loader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(properties.getProperty("rabbit.interval"));
    }

    public static void main(String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail jobDetail = newJob(Rabbit.class).build();
            SimpleScheduleBuilder times = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(AlertRabbit.readFileProperties())
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    public static class Rabbit implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
        }
    }
}
