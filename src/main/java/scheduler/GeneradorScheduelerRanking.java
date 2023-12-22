package scheduler;

import org.quartz.*;

public class GeneradorScheduelerRanking {
    public void comenzar() throws SchedulerException {
        // Creacion del scheduler
        SchedulerFactory schedFactory = new org.quartz.impl.StdSchedulerFactory();
        Scheduler scheduler = schedFactory.getScheduler();

        //Creacion del Trabajo a realizar
        JobBuilder jobBuilder = JobBuilder.newJob(JobNotificar.class);
        JobDetail jobDetail = jobBuilder.withIdentity("JobNotificar").build();

        //Creacion del triger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("TemporizadorNotificaciones")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 59 23 ? * SUN"))
                .build();

        //Asigno el trabajo y el trigger al schedueler
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();

    }
}
