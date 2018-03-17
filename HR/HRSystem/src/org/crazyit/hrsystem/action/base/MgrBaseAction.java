package org.crazyit.hrsystem.action.base;

import com.opensymphony.xwork2.ActionSupport;
import org.crazyit.hrsystem.service.EmpManager;

public class MgrBaseAction extends ActionSupport {
    //依赖的业务逻辑组件
    protected EmpManager mgr;

    //依赖注入业务逻辑组件所必须的setter方法
    public void setMgr(EmpManager mgr) {
        this.mgr = mgr;
    }
}
