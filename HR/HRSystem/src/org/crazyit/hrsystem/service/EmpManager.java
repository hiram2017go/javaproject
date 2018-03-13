package org.crazyit.hrsystem.service;

import org.crazyit.hrsystem.domain.AttendType;
import org.crazyit.hrsystem.domain.Manager;
import org.crazyit.hrsystem.vo.AttendBean;
import org.crazyit.hrsystem.vo.PaymentBean;

import java.util.List;

/**
 *
 */
public interface EmpManager {
    //登录失败
    public static final int LOGIN_FAIL = 0;
    //以普通员工登录
    public static final int LOGIN_EMP = 1;
    //以经理登录
    public static final int LOGIN_MGR = 2;

    //不能打卡
    public static final int NO_PUNCH = 0;
    //只能上班打卡
    public static final int COME_PUNCH = 1;
    //只能下班打卡
    public static final int LEAVE_PUNCH = 2;
    //上下班可打卡
    public static final int BOTH_PUNCH = 3;

    // 打卡失败
    public static final int PUNCH_FAIL = 0;
    // 已经打卡
    public static final int PUNCHED = 1;
    // 打卡成功
    public static final int PUNCH_SUCC = 2;
    //以上午11点为上午时间
    public  static final int AM_LIMIT = 11;

    //以上午9点之前为正常上班
    public static  final int COME_LIMIT = 9;
    //以上午9点~11点之间算迟到
    public static final int LATE_LIMIT = 11;
    //以下午18点之后算正常下班
    public static final int LEAVE_LIMIT = 18;
    //以下午16~18点之间算迟到
    public static final int EARLY_LIMIE = 16;


    /**
     * 以经身份来验证登录
     * @param mgr 登录的经理身份
     * @return 登录后的身份确认: 0 为登录失败， 1 为登录emp 2 为登录mgr
     */
    int valiadLogin(Manager mgr);


    /**
     * 自动打卡，周一到周五，每天早上7:00位每个员工插入旷工记录
     */
    void autoPunch();


    /**
     * 自动结算工资，每月1号，自动为每位员工结算上月工资
     */
    void autoPay();


    /**
     * 验证某个员工是否可打卡
     * @param user 员工名
     * @param dutyDay 打卡日期
     * @return 可打卡的类别
     */
    int validPunch(String user, String dutyDay);


    /**
     * 打卡
     * @param user 员工名
     * @param dutyDay 打卡日期
     * @param isCome 是否上班打卡
     * @return  打卡结果
     */
    int punch(String user, String dutyDay, boolean isCome);


    /**
     * 根据员工名册浏览自己的工资
     * @param empName 员工名称
     * @return 该员工的工资列表
     */
    List<PaymentBean> empSalary(String empName);


    /**
     * 员工查看自己的最近三天非正常打卡
     * @param empName 员工名
     * @return 该员工的最近三天的非正常打卡
     */
    List<AttendBean> unAttend(String empName);

    /**
     * 返回全部出勤类别
     * @return
     */
    List<AttendType> GetAllType();


    /**
     * 添加申请
     * @param attId 申请的出勤id
     * @param typeId 申请的类型id
     * @param reason 申请理由
     * @return 添加结果
     */
    boolean addApplication(int attId, int typeId, String reason);
}

