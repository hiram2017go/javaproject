package lee;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class TestJob implements Job {

    //判断作业是否执行的旗标
    private boolean isRunnng = false;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //如果作业没有被调度
        if(!isRunnng){
            System.out.println(new Date() + " 作业被调度.");

            //循环100次模拟任务的执行
            for(int i = 0; i < 100; i++){
                System.out.println("作业完成"+(i + 1) + '%');

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(new Date() + " 作业调度结束");
        }else{ //如果作业正在运行，即时获得调度，也立即退出
            System.out.println(new Date() + " 任务退出");
        }
    }
}
