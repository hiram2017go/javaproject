package org.crazyit.hrsystem.Dao.impl;

import org.crazyit.common.dao.impl.BaseDaoHibernate4;
import org.crazyit.hrsystem.Dao.ManagerDao;
import org.crazyit.hrsystem.domain.Manager;

import java.util.List;

public class ManagerDaoHibernate4 extends BaseDaoHibernate4<Manager> implements ManagerDao {
    /**
     * 根据用户名和密码查询经理
     *
     * @param mgr 包含指定用户名、密码的经理
     */
    @Override
    public List<Manager> findByNameAndPass(Manager mgr) {
        return find("select m from Manager m where m.name = ?0 and m.pass = ?1", mgr.getName(), mgr.getPass());
    }

    /**
     * 根据用户名查询经理
     *
     * @param name
     */
    @Override
    public Manager findByName(String name) {
        List<Manager> mgrList = find("select m from Manager m where m.name = ?0", name);
        if(mgrList != null && mgrList.size() > 0) return  mgrList.get(0);
        return null;
    }
}
