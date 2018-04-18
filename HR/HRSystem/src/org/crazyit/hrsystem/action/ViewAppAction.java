package org.crazyit.hrsystem.action;

import com.opensymphony.xwork2.ActionContext;
import org.crazyit.hrsystem.action.base.MgrBaseAction;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.List;

public class ViewAppAction extends MgrBaseAction {

    private List apps;

    public List getApps() {
        return apps;
    }

    public void setApps(List apps) {
        this.apps = apps;
    }

    @Override
    public String execute() throws Exception {
        //创建ActionContext实例
        ActionContext ctx = ActionContext.getContext();
        //获取HTTPSession中的user属性
        String mgrName = (String)ctx.getSession().get(WebConstant.USER);
        //获取需要被当前经理处理的全部申请
        setApps(mgr.getAppsByMgr(mgrName));
        System.out.println("----------------------------ViewAppAction--------------------------"+mgrName);
        return SUCCESS;
    }
}
