package org.crazyit.hrsystem.action;

import com.opensymphony.xwork2.ActionContext;
import org.crazyit.hrsystem.action.base.EmpBaseAction;
import org.crazyit.hrsystem.vo.AttendBean;

import java.util.List;

public class viewUnAttendAction extends EmpBaseAction {

    private List<AttendBean> unAttend;

    public List<AttendBean> getUnAttend() {
        return unAttend;
    }

    public void setUnAttend(List<AttendBean> unAttend) {
        this.unAttend = unAttend;
    }

    public String execute() throws Exception{
        //创建ActionContext实例
        ActionContext ctx = ActionContext.getContext();
        //获取HTTPSession中的user属性
        String user = (String)ctx.getSession().get(WebConstant.USER);

        List<AttendBean> result = mgr.unAttend(user);
        System.out.print("用户:"+user+";result="+result.size());
        setUnAttend(result);

        return SUCCESS;
    }
}
