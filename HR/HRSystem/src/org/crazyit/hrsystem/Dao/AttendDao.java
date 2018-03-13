package org.crazyit.hrsystem.Dao;

import org.crazyit.common.dao.BaseDao;
import org.crazyit.hrsystem.domain.Attend;
import org.crazyit.hrsystem.domain.AttendType;
import org.crazyit.hrsystem.domain.Employee;

import java.util.List;

public interface AttendDao extends BaseDao<Attend> {

    /**
    *  根据员工、月份查询该员工的出勤记录
    *  @param emp 员工
    *  @param month 月份
    */
    List<Attend> findByEmpAndMonth(Employee emp, String month);

    /**
     * 根据员工、日期查询该员工的打卡记录集合
     * @param emp 员工
     * @param dutyDay 日期
     * @return 该员工某天的打卡记录集合
     * */
    List<Attend> findByEmpAndDutyDay(Employee emp, String dutyDay);

    /**
    * 根据员工、日期、上下班查询该员工的打卡记录集合
    * @param emp 员工
    * @param dutyDay 日期
    * @param isCome 是否上班
    *
    * */
    Attend findByEmpAndDutyDayAndCome(Employee emp, String dutyDay, boolean isCome);

    /**
    * 查看员工前三天的非正常打卡
    * @param emp 员工
    * */
    List<Attend> findByEmpUnAttend(Employee emp, AttendType type);
}
