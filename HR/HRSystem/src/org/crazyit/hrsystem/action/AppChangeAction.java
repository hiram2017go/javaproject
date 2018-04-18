package org.crazyit.hrsystem.action;

import org.crazyit.hrsystem.action.base.EmpBaseAction;

import java.util.List;

public class AppChangeAction extends EmpBaseAction {

    //封装所有异动类型
    private List types;

    public List getTypes() {
        return types;
    }

    public void setTypes(List types) {
        this.types = types;
    }

    //处理用户请求
    @Override
    public String execute() throws Exception {
        setTypes(mgr.GetAllType());
        return SUCCESS;
    }
}
