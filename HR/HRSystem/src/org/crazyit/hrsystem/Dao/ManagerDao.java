package org.crazyit.hrsystem.Dao;

import org.crazyit.common.dao.BaseDao;
import org.crazyit.hrsystem.domain.Manager;

import java.util.List;

public interface ManagerDao extends BaseDao<Manager> {

    /**
     * 根据用户名和密码查询经理
     * @param mgr 包含指定用户名、密码的经理
     */
    List<Manager> findByNameAndPass(Manager mgr);

    /**
     * 根据用户名查询经理
     * */
    Manager findByName(String name);
}
