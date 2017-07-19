package hu.icell.listener;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import hu.icell.job.HelloJob;

@WebListener
public class QuartzListener implements ServletContextListener {
    Scheduler scheduler = null;

    public void contextInitialized(ServletContextEvent servletContext) {
        System.out.println("Context Initialized");

        try {
            // Setup the Job class and the Job group
            JobDetail job = newJob(HelloJob.class).withIdentity("CronQuartzJob", "Group").build();

            // Create a Trigger that fires every minutes.
            Trigger trigger = newTrigger().withIdentity("TriggerName", "Group").withSchedule(CronScheduleBuilder.cronSchedule("30 * * * * ?"))
                    .build();

            // Setup the Job and Trigger with Scheduler & schedule jobs
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContext) {
        System.out.println("Context Destroyed");
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
