package org.crazyit.hrsystem.action;

import org.crazyit.hrsystem.action.base.EmpBaseAction;

public class ProcessAppAction extends EmpBaseAction{

    //申请异动的出勤id
    private int attId;

    //申请改变到出勤类型
    private int typeId;

    //申请理由
    private String reason;

    public int getAttId() {
        return attId;
    }

    public void setAttId(int attId) {
        this.attId = attId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String execute() throws Exception {

        //处理异动申请
        boolean result = mgr.addApplication(attId, typeId, reason);

        //如果申请成功
        if(result){
            addActionMessage("您已经申请成功，等待经理审阅");
        }else{
            addActionMessage("申请失败，请注意不要重复申请");
        }

        return  SUCCESS;
    }
}
