package org.crazyit.hrsystem.Dao.impl;

import org.crazyit.common.dao.impl.BaseDaoHibernate4;
import org.crazyit.hrsystem.Dao.PaymentDao;
import org.crazyit.hrsystem.domain.Employee;
import org.crazyit.hrsystem.domain.Payment;

import java.util.List;

public class PaymentDaoHibernate4 extends BaseDaoHibernate4<Payment> implements PaymentDao {
    /**
     * 获取某个员工的薪水
     *
     * @param emp 员工
     * @return 员工对应薪水集合
     */
    @Override
    public List<Payment> findByEmp(Employee emp) {
        return find("select p from Payment as p where p.employee = ?0", emp);
    }

    /**
     * 获取某个员工某个月所发薪水集合
     *
     * @param month 发薪月份
     * @param emp   员工
     * @return 查询集合
     */
    @Override
    public Payment findByMonthAndEmp(String month, Employee emp) {
        List<Payment> pays = find("select p from Payment as p where p.employee = ?0 and p.payMonth = ?1", emp, month);
        if(pays != null && pays.size() > 0) return pays.get(0);

        return null;
    }
}
