package org.crazyit.hrsystem.action.base;

import com.opensymphony.xwork2.ActionSupport;
import org.crazyit.hrsystem.service.EmpManager;

public class EmpBaseAction extends ActionSupport {

    //依赖的业务逻辑组件
    protected EmpManager mgr;

    public void setMgr(EmpManager mgr) {
        this.mgr = mgr;
    }
}
