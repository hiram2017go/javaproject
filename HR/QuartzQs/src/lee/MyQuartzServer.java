package lee;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class MyQuartzServer {

    public static void main(String[] args){
        MyQuartzServer server = new MyQuartzServer();
        try {
            server.startScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void startScheduler() throws SchedulerException{
        //使用工厂创建调度器实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //以Job实现类创建JobDetail实例
        JobDetail jobDetail = JobBuilder.newJob(TestJob.class).withIdentity("fkJob").build();
        JobDetail jobDetail2 = JobBuilder.newJob(Test2Job.class).withIdentity("fk2Job").build();
        //创建Trigger对象，该对象代表一个简单的调度器
        //指定该任务呗重复调度50次，每次间隔60s.
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey("fkTrigger", "fkTriggerGroup"))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(60)
                        .repeatForever())
                .startNow()
                .build();

        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey("fkTrigger2", "fkTriggerGroup2"))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(60)
                        .repeatForever())
                .startNow()
                .build();
        //调度器将作业与Trigger关联起来
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.scheduleJob(jobDetail2, trigger2);
        //开始调度
        scheduler.start();
    }
}
