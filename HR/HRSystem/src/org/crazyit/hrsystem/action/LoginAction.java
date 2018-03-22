package org.crazyit.hrsystem.action;

import com.opensymphony.xwork2.ActionContext;
import org.crazyit.hrsystem.action.base.EmpBaseAction;
import org.crazyit.hrsystem.domain.Manager;
import static org.crazyit.hrsystem.service.EmpManager.*;

public class LoginAction extends EmpBaseAction {
    //定义一个常量作为员工登录成功的Result名
    private final String EMP_RESULT = "emp";
    //定义一个常量作为经理登录成功的Result名
    private final String MGR_RESULT = "mgr";
    //封装请求参数
    private Manager manager;
    //登录验证码
    private String vercode;

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getVercode() {
        return vercode;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

    //struts2继承ActionSupport后执行action方法
    @Override
    public String execute() throws Exception {

        System.out.println("---------------------------------");
        ActionContext ctx = ActionContext.getContext();
        //获取HTTPSession中的rand属性
        String ver2 = (String)ctx.getSession().get("rand");
        if(vercode.equalsIgnoreCase(ver2)){
            //调用业务逻辑方法来处理登录请求
            int result = mgr.valiadLogin(getManager());
            //登录结果为普通员工
            if(result == LOGIN_EMP){
                ctx.getSession().put(WebConstant.USER, manager.getName());
                ctx.getSession().put(WebConstant.LEVEL, WebConstant.EMP_LEVEL);
                addActionMessage("您已成功登录系统");
                return EMP_RESULT;
            }else if (result == LOGIN_MGR){//登录结果为经理
                ctx.getSession().put(WebConstant.USER, manager.getName());
                ctx.getSession().put(WebConstant.LEVEL, WebConstant.MGR_LEVEL);
                addActionMessage("您已成功登录系统");
                return MGR_RESULT;
            }else{//用户名和密码不匹配
                addActionMessage("用户名/密码不匹配");
                return ERROR;
            }
        }

        addActionMessage("验证码不匹配，请重新输入");
        return ERROR;
    }
}
