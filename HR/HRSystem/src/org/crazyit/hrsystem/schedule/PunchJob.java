package org.crazyit.hrsystem.schedule;

import org.crazyit.hrsystem.service.EmpManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

public class PunchJob extends QuartzJobBean {

    //判断作业是否正在执行
    private boolean isRunning = false;

    //该作业类所依赖的业务逻辑组件
    private EmpManager empMgr;

    public void setEmpMgr(EmpManager empMgr) {
        this.empMgr = empMgr;
    }

    //定义任务执行体
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        if(!isRunning){
            System.out.println("开始调度自动打卡.................."+ (new Date()));
            isRunning = true;
            //调用业务逻辑方法
            empMgr.autoPunch();
            isRunning = false;
        }
    }
}
