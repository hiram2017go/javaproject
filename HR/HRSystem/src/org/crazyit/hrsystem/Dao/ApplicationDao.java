package org.crazyit.hrsystem.Dao;

import org.crazyit.common.dao.BaseDao;
import org.crazyit.hrsystem.domain.Application;
import org.crazyit.hrsystem.domain.Employee;

import java.util.List;

public interface ApplicationDao extends BaseDao<Application> {

    /**
     * 根据员工查询未处理的异动申请
     * @param emp 需要查询的员工
     * @return 该员工对应的未处理的异动申请
     */
    List<Application> findByEmp(Employee emp);
}
