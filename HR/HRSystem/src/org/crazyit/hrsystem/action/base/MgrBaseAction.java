package org.crazyit.hrsystem.action.base;

import com.opensymphony.xwork2.ActionSupport;
import org.crazyit.hrsystem.service.MgrManager;

public class MgrBaseAction extends ActionSupport {
    //依赖的业务逻辑组件
    protected MgrManager mgr;

    //依赖注入业务逻辑组件所必须的setter方法

    public void setMgrManager(MgrManager mgr) {
        this.mgr = mgr;
    }
}
