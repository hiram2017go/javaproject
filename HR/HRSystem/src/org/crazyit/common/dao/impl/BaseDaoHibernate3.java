package org.crazyit.common.dao.impl;

import org.crazyit.common.dao.BaseDao;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.List;

public class BaseDaoHibernate3<T> extends HibernateDaoSupport implements BaseDao<T> {

    //根据ID加载实体
    @Override
    public T get(Class<T > entityClazz, Serializable id) {
        return getHibernateTemplate().get(entityClazz, id);
    }

    //保存实体
    @Override
    public Serializable save(T entity) {
        return getHibernateTemplate().save(entity);
    }

    @Override
    public void update(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    @Override
    public void delete(Class<T> entityClazz, Serializable id) {
        delete(get(entityClazz, id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll(Class<T> entityClazz) {
        return (List<T>)getHibernateTemplate().find("select en from " + entityClazz.getSimpleName() + " en");
    }

    @Override
    @SuppressWarnings("unchecked")
    public long findCount(Class<T> entityClazz) {
        List<Long> list = (List<Long>)getHibernateTemplate().find("select count(*) from " + entityClazz.getSimpleName() + " en");
        return list.get(0);
    }

    /*
    * 使用hql分页查询操作
    * @param hql 需要查询的hql语句
    * @param pageNo 查询第pageNo的记录
    * @param pageSize 每页需要显示的记录数
    * */
    @SuppressWarnings("unchecked")
    protected List<T> findByPage(final String hql, final int pageNo, final int pageSize){
        // 实现HibernateCallback接口必须实现的方法
        List<T> list = getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                List<T> result = session.createQuery(hql)
                        .setFirstResult((pageNo - 1) * pageSize)
                        .setMaxResults(pageSize)
                        .list();
                return result;
            }
        });
        return list;
    }

    /*
    * 使用hql分页查询操作
    * @param hql 需要查询的hql语句
    * @param pageNo 查询第pageNo的记录
    * @param pageSize 每页需要显示的记录数
    * @param params 如果hql带占位符参数，params用于传入占位参数
    * */
    @SuppressWarnings("unchecked")
    protected List<T> findByPage(final String hql, final  int pageNo, final  int pageSize, final  Object... params){
        // 实现HibernateCallback接口必须实现的方法
        List<T> list = getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                //执行Hibernate分页查询
                Query query = session.createQuery(hql);
                //为包含占位符的HQL语句设置参数
                for(int i = 0, len = params.length; i < len; i++){
                    query.setParameter(i + "",  params[i]);
                }
                List<T> result = query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();

                return result;
            }
        });

        return list;
    }
}
