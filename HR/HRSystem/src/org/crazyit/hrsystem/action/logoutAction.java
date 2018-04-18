package org.crazyit.hrsystem.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.*;
import org.apache.struts2.interceptor.*;
import org.crazyit.hrsystem.action.base.EmpBaseAction;

public class logoutAction extends EmpBaseAction implements ServletRequestAware {

    private HttpServletRequest request;
    //定义一个HttpServletRequest对象
    @Override
    public void setServletRequest(HttpServletRequest request) {

        this.request = request;
    }

    @Override
    public String execute() throws Exception {
        HttpSession session = request.getSession();
        session.invalidate();//使session失效
        return SUCCESS;
    }
}
