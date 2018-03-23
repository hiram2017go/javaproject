package org.crazyit.hrsystem.service.impl;

import org.crazyit.hrsystem.Dao.*;
import org.crazyit.hrsystem.domain.*;
import org.crazyit.hrsystem.exception.HrException;
import org.crazyit.hrsystem.service.MgrManager;
import org.crazyit.hrsystem.vo.AppBean;
import org.crazyit.hrsystem.vo.EmpBean;
import org.crazyit.hrsystem.vo.SalaryBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class MgrManagerImpl implements MgrManager {

    private ApplicationDao appDao;
    private AttendDao attendDao;
    private AttendTypeDao attendTypeDao;
    private EmployeeDao employeeDao;
    private ManagerDao mgrDao;
    private CheckBackDao checkBackDao;
    private PaymentDao paymentDao;

    public ApplicationDao getAppDao() {
        return appDao;
    }

    public void setAppDao(ApplicationDao appDao) {
        this.appDao = appDao;
    }

    public AttendDao getAttendDao() {
        return attendDao;
    }

    public void setAttendDao(AttendDao attendDao) {
        this.attendDao = attendDao;
    }

    public AttendTypeDao getAttendTypeDao() {
        return attendTypeDao;
    }

    public void setAttendTypeDao(AttendTypeDao attendTypeDao) {
        this.attendTypeDao = attendTypeDao;
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public ManagerDao getMgrDao() {
        return mgrDao;
    }

    public void setMgrDao(ManagerDao mgrDao) {
        this.mgrDao = mgrDao;
    }

    public CheckBackDao getCheckBackDao() {
        return checkBackDao;
    }

    public void setCheckBackDao(CheckBackDao checkBackDao) {
        this.checkBackDao = checkBackDao;
    }

    public PaymentDao getPaymentDao() {
        return paymentDao;
    }

    public void setPaymentDao(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    /**
     * 新增员工
     *
     * @param emp 新增的员工
     * @param mgr 员工所属的经理
     * @throws HrException
     */
    @Override
    public void addEmp(Employee emp, String mgr) throws HrException {
        Manager manager = mgrDao.findByName(mgr);
        if(manager == null){
            throw new HrException("您是经理吗？或您还未登录?");
        }

        emp.setManager(manager);
        employeeDao.save(emp);
    }

    /**
     * 根据经理返回所有的部门上个月工资
     *
     * @param mgr 经理名
     * @return 部门上个月工资
     */
    @Override
    public List<SalaryBean> getSalaryByMgr(String mgr) {
        Manager manager = mgrDao.findByName(mgr);
        if(manager == null){
            throw new HrException("您是经理吗？或您还未登录?");
        }

        Set<Employee> emps = manager.getEmployees();
        if(emps == null || emps.size() == 0){
            throw new HrException("您的部门暂时还没有员工，请先增加员工");
        }
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String payMonth = sdf.format(c.getTime());
        List<SalaryBean> result = new ArrayList<SalaryBean>();
        //遍历部门每个员工
        for(Employee e : emps){
            Payment payment = paymentDao.findByMonthAndEmp(payMonth, e);
            if(payment != null){
                result.add(new SalaryBean(e.getName(), payment.getAmount()));
            }
        }

        return result;
    }

    /**
     * 根据经理返回该部门的全部员工
     *
     * @param mgr 经理名
     * @return
     */
    @Override
    public List<EmpBean> getEmpsByMgr(String mgr) {

        Manager manager = mgrDao.findByName(mgr);
        if(manager == null){
            throw new HrException("您是经理吗？或您还未登录?");
        }

        //查询该经理对应的全部员工
        Set<Employee> emps = manager.getEmployees();
        if(emps == null || emps.size() == 0){
            throw new HrException("您的部门暂时还没有员工，请先增加员工");
        }

        List<EmpBean> empBeanList = new ArrayList<EmpBean>();
        for(Employee e : emps){
            empBeanList.add(new EmpBean(e.getName(), e.getPass(), e.getSalary()));
        }

        return empBeanList;
    }

    /**
     * 根据经理返回该部门没有批复的申请
     *
     * @param mgr 经理名
     * @return
     */
    @Override
    public List<AppBean> getAppsByMgr(String mgr) {

        Manager manager = mgrDao.findByName(mgr);
        if(manager == null){
            throw new HrException("您是经理吗？或您还未登录?");
        }

        Set<Employee> emps = manager.getEmployees();
        if(emps == null || emps.size() == 0){
            throw new HrException("您的部门暂时还没有员工，请先增加员工");
        }

        List<AppBean> appBeanList = new ArrayList<AppBean>();
        for(Employee e : emps){
            List<Application> applicationList = appDao.findByEmp(e);
            if(applicationList.size() > 0){
                for(Application a : applicationList){
                    if(!a.getIsResult()){//只选择还未处理的申请
                        Attend attend = a.getAttend();
                        appBeanList.add(new AppBean(a.getId(), e.getName(), attend.getType().getName(), a.getType().getName(), a.getReason()));
                    }
                }
            }
        }

        return appBeanList;
    }

    /**
     * 处理申请
     *
     * @param appid   申请id
     * @param mgrName 经理名称
     * @param result  是否通过
     */
    @Override
    public void check(int appid, String mgrName, boolean result) {
        Application application = appDao.get(Application.class, appid);
        CheckBack checkBack = new CheckBack();
        checkBack.setApp(application);
        Manager mgr = mgrDao.findByName(mgrName);
        if(mgr == null){
            throw new HrException("你是经理吗?");
        }

        checkBack.setManager(mgr);
        if(result){//同意申请
            //设置通过申请
            checkBack.setResult(true);

            //修改申请为已批复
            application.setIsResult(true);

            appDao.update(application);

            //修改出勤的类型
            Attend attend = application.getAttend();
            attend.setType(application.getType());
            attendDao.update(attend);
        }else {
            //没有通过申请
            checkBack.setResult(false);
            application.setIsResult(true);

            appDao.update(application);
        }

        checkBackDao.save(checkBack);//保存申请批复
    }
}
