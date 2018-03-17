package org.crazyit.hrsystem.schedule;

import org.crazyit.hrsystem.service.EmpManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class PayJob extends QuartzJobBean {

    private boolean isRunning = false;

    //该作业所依赖的业务逻辑组件
    private EmpManager empMrg;

    public void setEmpMrg(EmpManager empMrg) {
        this.empMrg = empMrg;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        if(!isRunning){
            System.out.println("开始调度自动结算工资");
            isRunning = true;
            empMrg.autoPay();
            isRunning = false;
        }
    }
}
