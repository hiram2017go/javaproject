package org.crazyit.hrsystem.Dao.impl;

import org.crazyit.common.dao.impl.BaseDaoHibernate4;
import org.crazyit.hrsystem.Dao.ApplicationDao;
import org.crazyit.hrsystem.domain.Application;
import org.crazyit.hrsystem.domain.Employee;

import java.util.List;

public class ApplicationDaoHibernate4 extends BaseDaoHibernate4<Application> implements ApplicationDao {
    /**
     * 根据员工查询未处理的异动申请
     *
     * @param emp 需要查询的员工
     * @return 该员工对应的未处理的异动申请
     */
    @Override
    public List<Application> findByEmp(Employee emp) {
        return find("select a from Application as a where a.attend.employee=?0", emp);
    }
}
