package org.crazyit.hrsystem.Dao;

import org.crazyit.common.dao.BaseDao;
import org.crazyit.hrsystem.domain.Employee;
import org.crazyit.hrsystem.domain.Payment;

import java.util.List;

public interface PaymentDao extends BaseDao<Payment> {


    /** 获取某个员工的薪水
     * @param emp 员工
     * @return 员工对应薪水集合
     */
    List<Payment> findByEmp(Employee emp);


    /** 获取某个员工某个月所发薪水集合
     * @param month 发薪月份
     * @param emp 员工
     * @return 员工该月所发薪水
     * */
    Payment findByMonthAndEmp(String month, Employee emp);
}
