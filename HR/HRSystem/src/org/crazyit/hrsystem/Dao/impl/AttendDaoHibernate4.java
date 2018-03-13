package org.crazyit.hrsystem.Dao.impl;

import org.crazyit.common.dao.impl.BaseDaoHibernate4;
import org.crazyit.hrsystem.Dao.AttendDao;
import org.crazyit.hrsystem.domain.Attend;
import org.crazyit.hrsystem.domain.AttendType;
import org.crazyit.hrsystem.domain.Employee;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AttendDaoHibernate4 extends BaseDaoHibernate4<Attend> implements AttendDao {
    private String x;
    /**
     * 根据员工、月份查询该员工的出勤记录
     *
     * @param emp   员工
     * @param month 月份
     */
    @Override
    public List<Attend> findByEmpAndMonth(Employee emp, String month) {
        return find("from Attend as a where a.employee = ?0 and substring(a.dutyDay, 0, 7) = ?1", emp, month);
    }

    /**
     * 根据员工、日期查询该员工的打卡记录集合
     *
     * @param emp     员工
     * @param dutyDay 日期
     * @return 该员工某天的打卡记录集合
     */
    @Override
    public List<Attend> findByEmpAndDutyDay(Employee emp, String dutyDay) {
        return find("from Attend as a where a.employee = ?0 and a.dutyDay = ?1", emp, dutyDay);
    }

    /**
     * 根据员工、日期、上下班查询该员工的打卡记录集合
     *
     * @param emp     员工
     * @param dutyDay 日期
     * @param isCome  是否上班
     */
    @Override
    public Attend findByEmpAndDutyDayAndCome(Employee emp, String dutyDay, boolean isCome) {

        List<Attend> al = findByEmpAndDutyDay(emp, dutyDay);
        for(Attend attend : al){
            if(attend.getIsCome() == isCome) return attend;
        }
        return null;
    }

    /**
     * 查看员工前三天的非正常打卡
     *
     * @param emp  员工
     * @param type
     */
    @Override
    public List<Attend> findByEmpUnAttend(Employee emp, AttendType type) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String end = sdf.format(c.getTime());
        c.add(Calendar.DAY_OF_MONTH, -3);
        String start = sdf.format(c.getTime());

        return find("from Attend as a where a.employee = ?0 and a.type != ?1 and a.dutyDay between ?2 and ?3", emp, type, start, end);
    }
}
