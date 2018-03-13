package org.crazyit.hrsystem.service;

import org.crazyit.hrsystem.domain.Employee;
import org.crazyit.hrsystem.exception.HrException;
import org.crazyit.hrsystem.vo.AppBean;
import org.crazyit.hrsystem.vo.EmpBean;
import org.crazyit.hrsystem.vo.SalaryBean;

import java.util.List;

public interface MgrManager {

    /** 新增员工
     * @param emp 新增的员工
     * @param mgr 员工所属的经理
     * @throws HrException
     */
    void addEmp(Employee emp, String mgr) throws HrException;

    /** 根据经理返回所有的部门上个月工资
     * @param mgr 经理名
     * @return 部门上个月工资
     */
    List<SalaryBean> getSalaryByMgr(String mgr);

    /**
     * 根据经理返回该部门的全部员工
     * @param mgr 经理名
     * @return
     */
    List<EmpBean> getEmpsByMgr(String mgr);

    /**
     * 根据经理返回该部门没有批复的申请
     * @param mgr 经理名
     * @return
     */
    List<AppBean> getAppsByMgr(String mgr);


    /**
     * 处理申请
     * @param appid 申请id
     * @param mgrName 经理名称
     * @param result 是否通过
     */
    void check(int appid, String mgrName, boolean result);
}
