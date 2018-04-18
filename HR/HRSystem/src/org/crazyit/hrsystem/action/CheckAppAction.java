package org.crazyit.hrsystem.action;

import com.opensymphony.xwork2.ActionContext;
import org.crazyit.hrsystem.action.base.MgrBaseAction;

public class CheckAppAction extends MgrBaseAction {

    //需要被处理的申请id
    private int appid;

    //封装处理结果
    private String result;

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String execute() throws Exception {

        //创建ActionContext实例
        ActionContext ctx = ActionContext.getContext();

        String mgrName = (String)ctx.getSession().get(WebConstant.USER);

        //通过申请
        if(result.equals("pass")){
            mgr.check(appid, mgrName, true);
        }else if(result.equals("deny")){//拒绝申请
            mgr.check(appid, mgrName, false);
        }else {
            throw new Exception("参数丢失");
        }

        return SUCCESS;
    }
}
