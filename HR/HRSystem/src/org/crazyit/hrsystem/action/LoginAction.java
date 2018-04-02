package org.crazyit.hrsystem.action;

import com.opensymphony.xwork2.ActionContext;
import org.crazyit.hrsystem.action.base.EmpBaseAction;
import org.crazyit.hrsystem.domain.AttendType;
import org.crazyit.hrsystem.domain.Manager;

import java.util.List;

import static org.crazyit.hrsystem.service.EmpManager.*;

public class LoginAction extends EmpBaseAction {
    //???????????????Result?
    private final String EMP_RESULT = "emp";
    //???????????????Result?
    private final String MGR_RESULT = "mgr";
    //??????
    private Manager manager;

    public Manager getManager() {
        return manager;
    }
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    //struts2??ActionSupport???action??
    @Override
    public String execute() throws Exception {

        System.out.println("---------------------------------");
        ActionContext ctx = ActionContext.getContext();
        //??HTTPSession??rand??
//        String ver2 = (String)ctx.getSession().get("rand");
//        if(vercode.equalsIgnoreCase(ver2)){
            //???????????????
        if(mgr == null) System.out.println("is null ???????");
        else System.out.println("not null ?????????");
        int result = mgr.valiadLogin(getManager());
        //?????????
        if(result == LOGIN_EMP){
            ctx.getSession().put(WebConstant.USER, manager.getName());
            ctx.getSession().put(WebConstant.LEVEL, WebConstant.EMP_LEVEL);
            addActionMessage("????????");
            return EMP_RESULT;
        }else if (result == LOGIN_MGR){//???????
            ctx.getSession().put(WebConstant.USER, manager.getName());
            ctx.getSession().put(WebConstant.LEVEL, WebConstant.MGR_LEVEL);
            addActionMessage("????????");
            return MGR_RESULT;
        }else{//?????????
            addActionMessage("???/?????");
            return ERROR;
        }
//        }

        //addActionMessage("????????????");
       // return ERROR;
    }
}
