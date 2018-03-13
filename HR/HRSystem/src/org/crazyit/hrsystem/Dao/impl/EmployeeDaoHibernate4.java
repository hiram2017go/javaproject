package org.crazyit.hrsystem.Dao.impl;

import org.crazyit.common.dao.impl.BaseDaoHibernate4;
import org.crazyit.hrsystem.Dao.EmployeeDao;
import org.crazyit.hrsystem.domain.Employee;

import java.util.List;

public class EmployeeDaoHibernate4 extends BaseDaoHibernate4<Employee> implements EmployeeDao {
    /**
     * 根据用户名和密码查询员工
     *
     * @param emp 包含指定用户名、密码的员工
     * @return 符合指定用户名和密码的员工集合
     */
    @Override
    public List<Employee> findByNameAndPass(Employee emp) {
        return find("select p from Employee p where p.name = ?0 and p.pass = ?1", emp.getName(), emp.getPass());
    }

    /**
     * 根据用户名查询员工
     *
     * @param name 员工用户名
     */
    @Override
    public Employee findByName(String name) {
        List<Employee> empList = find("select e from Employee e where e.name = ?0", name);
        if(empList != null && empList.size() > 0) return empList.get(0);

        return null;
    }
}
