package org.crazyit.hrsystem.action;

import com.opensymphony.xwork2.ActionContext;
import org.crazyit.hrsystem.action.base.EmpBaseAction;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PunchAction extends EmpBaseAction {

    //封装处理结果的punchIsValid成员变量
    private int punchIsValid;

    public int getPunchIsValid() {
        return punchIsValid;
    }

    public void setPunchIsValid(int punchIsValid) {
        this.punchIsValid = punchIsValid;
    }

    @Override
    public String execute() throws Exception {
        //创建ActionContext实例
        ActionContext ctx = ActionContext.getContext();
        //获取HTTPSession中的user属性
        String user = (String)ctx.getSession().get(WebConstant.USER);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //格式化当前时间
        String dutyDay = sdf.format(new Date());
        //调用业务逻辑方法处理用户请求
        int result = mgr.validPunch(user, dutyDay);
        System.out.println("------------"+dutyDay);
        setPunchIsValid(result);
        return SUCCESS;
    }
}
