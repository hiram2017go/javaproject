package org.crazyit.hrsystem.Dao;

import org.crazyit.common.dao.BaseDao;
import org.crazyit.hrsystem.domain.Employee;

import java.util.List;

public interface EmployeeDao extends BaseDao<Employee> {

    /**
     * 根据用户名和密码查询员工
     * @param emp 包含指定用户名、密码的员工
     * @return 符合指定用户名和密码的员工集合
    */
    List<Employee> findByNameAndPass(Employee emp);

    /**
     * 根据用户名查询员工
     * @param name 员工用户名
     */
    Employee findByName(String name);
}
