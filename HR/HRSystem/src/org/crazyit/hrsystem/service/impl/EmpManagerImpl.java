package org.crazyit.hrsystem.service.impl;

import org.crazyit.hrsystem.Dao.*;
import org.crazyit.hrsystem.domain.*;
import org.crazyit.hrsystem.service.EmpManager;
import org.crazyit.hrsystem.vo.AttendBean;
import org.crazyit.hrsystem.vo.PaymentBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmpManagerImpl implements EmpManager {

    private ApplicationDao appDao;
    private AttendDao attendDao;
    private AttendTypeDao attendTypeDao;
    private CheckBackDao checkBackDao;
    private EmployeeDao employeeDao;
    private ManagerDao mgrDao;
    private PaymentDao paymentDao;

    //以下注入所有Dao组件
    public void setAppDao(ApplicationDao appDao) {
        this.appDao = appDao;
    }

    public void setAttendDao(AttendDao attendDao) {
        this.attendDao = attendDao;
    }

    public void setAttendTypeDao(AttendTypeDao attendTypeDao) {
        this.attendTypeDao = attendTypeDao;
    }

    public void setCheckBackDao(CheckBackDao checkBackDao) {
        this.checkBackDao = checkBackDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void setMgrDao(ManagerDao mgrDao) {
        this.mgrDao = mgrDao;
    }

    public void setPaymentDao(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    /**
     * 以经身份来验证登录
     *
     * @param mgr 登录的经理身份
     * @return 登录后的身份确认: 0 为登录失败， 1 为登录emp 2 为登录mgr
     */
    @Override
    public int valiadLogin(Manager mgr) {
        //如果找到一个经理，以经理身份登录
        if(mgrDao.findByNameAndPass(mgr).size() > 0) return  LOGIN_MGR;

        //如果找到普通员工，则以普通员工身份登录
        if(employeeDao.findByNameAndPass(mgr).size() > 0) return LOGIN_EMP;

        return LOGIN_FAIL;
    }

    /**
     * 自动打卡，周一到周五，每天早上7:00位每个员工插入旷工记录
     */
    @Override
    public void autoPunch() {
        System.out.println("自动插入旷工记录");

        List<Employee> empList = employeeDao.findAll(Employee.class);
        String dutyDay = new Date(System.currentTimeMillis()).toString();

        for (Employee e : empList){
            AttendType attendType = attendTypeDao.get(AttendType.class, 6);
            Attend attend = new Attend();
            attend.setDutyDay(dutyDay);
            attend.setEmployee(e);
            //如果当前时间是早上，对应于上班打卡
            if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < AM_LIMIT){
                attend.setIsCome(true);//上班打卡
            }else attend.setIsCome(false);//下班打卡
            attendDao.save(attend);
        }
    }

    /**
     * 自动结算工资，每月1号，自动为每位员工结算上月工资
     */
    @Override
    public void autoPay() {
        System.out.println("自动插入工资结算");

        List<Employee> empList = employeeDao.findAll(Employee.class);

        //获取上个月时间
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -15);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String payMonth = sdf.format(c.getTime());

        //为每个员工计算上个月工资
        for (Employee e : empList){
            Payment payment = new Payment();
            //获取该员工的工资
            double amount = e.getSalary();
            //获取该员工上个月的出勤记录
            List<Attend> attendList = attendDao.findByEmpAndMonth(e, payMonth);
            //用工资累积出其出勤记录的工资
            for (Attend a : attendList){
                amount += a.getType().getAmerce();
            }

            //添加工资结算
            payment.setPayMonth(payMonth);
            payment.setEmployee(e);
            payment.setAmount(amount);

            paymentDao.save(payment);
        }
    }

    /** 上下班打卡是punchTime都为null.
     * 验证某个员工是否可打卡
     *
     * @param user    员工名
     * @param dutyDay 打卡日期
     * @return 可打卡的类别
     */
    @Override
    public int validPunch(String user, String dutyDay) {

        //查询不到此员工，说明不能打卡
        Employee emp = employeeDao.findByName(user);
        if(emp == null) return  NO_PUNCH;

        //找到员工的出勤记录
        List<Attend> attendList = attendDao.findByEmpAndDutyDay(emp, dutyDay);
        if(attendList == null || attendList.size() == 0) return NO_PUNCH;

        if(attendList.size() == 1 && attendList.get(0).getIsCome() && attendList.get(0).getPunchTime() == null) return COME_PUNCH;

        if(attendList.size() == 1 && attendList.get(0).getPunchTime() == null) return LEAVE_PUNCH;

        if(attendList.size() == 2){
            if(attendList.get(0).getPunchTime() == null && attendList.get(1).getPunchTime() == null) return  BOTH_PUNCH;
            if(attendList.get(1).getPunchTime() == null) return LEAVE_PUNCH;
        }

        return NO_PUNCH;
    }

    /**
     * 打卡
     *
     * @param user    员工名
     * @param dutyDay 打卡日期
     * @param isCome  是否上班打卡
     * @return 打卡结果
     */
    @Override
    public int punch(String user, String dutyDay, boolean isCome) {
        Employee emp = employeeDao.findByName(user);
        if(emp == null) return PUNCH_FAIL;

        //找到该员工当前时间的出勤记录
        Attend attend = attendDao.findByEmpAndDutyDayAndCome(emp, dutyDay, isCome);
        if(attend == null) return PUNCH_FAIL;

        //已经打卡
        if(attend.getPunchTime() == null) return  PUNCHED; //系统每天定时7点和13点为每个员工各插入一条旷工记录，但是punchtime为空。员工打卡时是修改那个时间

        System.out.print("嘀·已打卡~~~~~~~~~~~~~~~");

        int punchHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        attend.setPunchTime(new Date());
        //上班打卡
        if(isCome){
            //9点之前算正常
            if(punchHour < COME_LIMIT){
                attend.setType(attendTypeDao.get(AttendType.class, 1));
            }else if(punchHour < LATE_LIMIT){  //9~11点算迟到
                attend.setType(attendTypeDao.get(AttendType.class, 4));
            }
            //11点之后算旷工，不登记
        }else{
            //18点之后算正常
            if(punchHour >= LEAVE_LIMIT){
                attend.setType(attendTypeDao.get(AttendType.class, 1));
            }else if(punchHour >= EARLY_LIMIE){
                attend.setType(attendTypeDao.get(AttendType.class, 5)); //16~18点算早退
            }
        }
        attendDao.update(attend);
        return PUNCH_SUCC;
    }

    /**
     * 根据员工名册浏览自己的工资
     *
     * @param empName 员工名称
     * @return 该员工的工资列表
     */
    @Override
    public List<PaymentBean> empSalary(String empName) {

        Employee emp = employeeDao.findByName(empName);
        if(emp == null) return null;

        //获取该员工的全部工资列表
        List<Payment> payList = paymentDao.findByEmp(emp);
        if(payList == null) return null;
        List<PaymentBean> result = new ArrayList<PaymentBean>();
        //封装VO集合
        for(Payment p: payList){
            result.add(new PaymentBean(p.getPayMonth(), p.getAmount()));
        }
        return result;
    }

    /**
     * 员工查看自己的最近三天非正常打卡
     *
     * @param empName 员工名
     * @return 该员工的最近三天的非正常打卡
     */
    @Override
    public List<AttendBean> unAttend(String empName) {
        //找出正常上班
        AttendType attendType = attendTypeDao.get(AttendType.class, 1);
        Employee emp = employeeDao.findByName(empName);
        if(emp == null) return null;
        //找出非正常上班的出勤记录
        List<Attend> attends = attendDao.findByEmpUnAttend(emp, attendType);
        List<AttendBean> attendBeanList = new ArrayList<AttendBean>();

        //封装VO集合
        for(Attend att : attends){
            attendBeanList.add(new AttendBean(att.getId(), att.getDutyDay(), att.getType().getName(), att.getPunchTime()));
        }

        return attendBeanList;
    }

    /**
     * 返回全部出勤类别
     *
     * @return
     */
    @Override
    public List<AttendType> GetAllType() {

        return attendTypeDao.findAll(AttendType.class);
    }

    /**
     * 添加申请
     *
     * @param attId  申请的出勤id
     * @param typeId 申请的类型id
     * @param reason 申请理由
     * @return 添加结果
     */
    @Override
    public boolean addApplication(int attId, int typeId, String reason) {
        System.out.println("-----------------attId:" + attId);
        System.out.println("-----------------typeId:" + typeId);
        System.out.println("-----------------reason:" + reason);
        //创建一个申请
        Application app = new Application();
        //获取申请需要改变的出勤记录
        Attend attend = attendDao.get(Attend.class, attId);
        AttendType attendType = attendTypeDao.get(AttendType.class, typeId);
        app.setAttend(attend);
        app.setType(attendType);
        if(reason != null){
            app.setReason(reason);
        }
        appDao.save(app);
        System.out.println("添加完成~~~~~~~~~~~");

        return true;
    }
}
