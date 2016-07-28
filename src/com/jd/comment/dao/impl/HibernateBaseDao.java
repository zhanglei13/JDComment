package com.jd.comment.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jd.comment.dao.IHibernateBaseDao;
import com.jd.comment.util.ListPage;
import com.jd.comment.util.Page;
import com.jd.comment.util.ReflectUtils;

@Repository("hibernateBaseDao")
public class HibernateBaseDao<T, PK extends Serializable> implements IHibernateBaseDao<T, PK> {

	protected final Logger logger = Logger.getLogger(getClass());

	@Autowired
	private SessionFactory sessionFactory;

	protected Class<T> entityClass;

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public HibernateBaseDao() {
		entityClass = ReflectUtils.getClassGenricType(getClass());
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
		// return sessionFactory.openSession();
	}

	@SuppressWarnings("unchecked")
	public T get(PK id) {
		return (T) this.getSession().get(entityClass, id);

	}

	@SuppressWarnings("unchecked")
	public <X> X get(Class<X> clazz, PK id) {
		return (X) this.getSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public T load(PK id) {
		return (T) this.getSession().load(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public <X> X load(Class<X> clazz, PK id) {
		return (X) this.getSession().load(clazz, id);
	}

	public void delete(T entity) {
		this.getSession().delete(entity);

	}

	public void deleteAll(Collection<T> entities) {
		for (T entity : entities) {
			this.getSession().delete(entity);
		}

	}

	public void deleteById(PK id) {
		T t = get(id);
		delete(t);
	}

	public void deleteByIds(PK[] ids) {
		for (PK id : ids) {
			deleteById(id);
		}
	}

	public void save(T entity) {
		this.getSession().save(entity);

	}

	public void saveAll(Collection<T> entities) {
		for (T entity : entities) {
			save(entity);
		}
	}

	public void saveOrUpdate(T entity) {
		this.getSession().saveOrUpdate(entity);
	}

	public <X> void saveOrUpdate(Class<X> clazz, X entity) {
		this.getSession().saveOrUpdate(entity);
	}

	public void saveOrUpdateAll(Collection<T> entities) {
		for (T entity : entities) {
			saveOrUpdate(entity);
		}

	}

	public void persist(T entity) {
		this.getSession().persist(entity);
	}

	public void update(T entity) {
		this.getSession().update(entity);
	}

	public void updateAll(Collection<T> entities) {
		for (T entity : entities) {
			update(entity);
		}

	}

	public void flush() {
		this.getSession().flush();

	}

	public void clear() {
		this.getSession().clear();

	}

	public void refresh(T entity) {
		this.getSession().refresh(entity);

	}

	public ListPage<T> findHqlListPage(final String hql, final Page page) {
		return findHqlListPage(hql, page, null);
	}

	public ListPage<T> findHqlListPage(final String hql, final Page page, final Object obj) {
		List<T> list = findHql(hql, page, obj);
		page.setRowCount(list.size());
		return new ListPage<T>(list, page);
	}

	public <X> List<X> findHql(String hql, Class<X> clazz) {
		return getQueryList(hql, -1, -1, null, clazz, 2);
	}

	public <X> List<X> findHql(String hql, Object param, Class<X> clazz) {
		return getQueryList(hql, -1, -1, param, clazz, 2);
	}

	public List<T> findHql(String hql) {
		return findHql(hql, null);
	}

	public List<T> findHql(final String hql, Object obj) {
		return findHql(hql, -1, -1, obj);
	}

	public List<T> findHql(final String hql, final long pageIndex, final long pageSize) {
		return findHql(hql, pageIndex, pageSize, null);
	}

	public List<T> findHql(final String hql, final Page page, final Object obj) {
		return this.findHql(hql, page.getCurrentRow(), page.getPageSize(), obj);
	}

	public List<T> findHql(final String hql, final long pageIndex, final long pageSize, final Object obj) {
		return this.getQueryList(hql, pageIndex, pageSize, obj, 2);
	}

	protected List<T> getQueryList(final String queryString, final long pageIndex, final long pageSize,
			final Object obj, final long type) {
		return getQueryList(queryString, pageIndex, pageSize, obj, entityClass, type);
	}

	public long getHqlRowCount(String hql) {
		return getHqlRowCount(hql, null);
	}

	public long getHqlRowCount(String hql, Object obj) {
		if (!hql.trim().toLowerCase().startsWith("from")) {
			hql = hql.substring(hql.toLowerCase().indexOf("from "));
		}
		hql = "select count(*) " + hql;
		List<?> list = this.findHql(hql, obj);
		long row = ((Long) list.get(0)).intValue();
		return row;
	}

	@SuppressWarnings("unchecked")
	protected <X> List<X> getQueryList(final String queryString, final long pageIndex, final long pageSize,
			final Object obj, final Class<X> clazz, final long type) {

		Query query = getQuery(queryString, pageIndex, pageSize, obj, type, this.getSession(), clazz);
		return query.list();
	}

	@SuppressWarnings("rawtypes")
	public List findByCriteria(final DetachedCriteria criteria, final long firstResult, final long maxResults) {

		Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
		if (firstResult >= 0) {
			executableCriteria.setFirstResult((int) firstResult);
		}
		if (maxResults > 0) {
			executableCriteria.setMaxResults((int) maxResults);
		}
		return executableCriteria.list();
	}

	@SuppressWarnings("rawtypes")

	public List findByCriteria(DetachedCriteria criteria) {
		return findByCriteria(criteria, -1, -1);
	}

	@SuppressWarnings("unchecked")

	public <X> List<X> findAll(Class<X> entityClass, final Criterion[] criterions, final Order[] orderBys,
			long firstResult, long maxResults) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entityClass);
		if (criterions != null) {
			for (Criterion c : criterions) {
				detachedCriteria.add(c);
			}
		}
		if (orderBys != null) {
			for (Order o : orderBys) {
				detachedCriteria.addOrder(o);
			}
		}
		return findByCriteria(detachedCriteria, firstResult, maxResults);
	}

	@SuppressWarnings("unchecked")

	public <X> List<X> findAll(Class<X> entityClass, String orderBy, boolean isAsc) {
		if (isAsc)
			return findByCriteria(DetachedCriteria.forClass(entityClass).addOrder(Order.asc(orderBy)));
		else
			return findByCriteria(DetachedCriteria.forClass(entityClass).addOrder(Order.desc(orderBy)));
	}

	public <X> List<X> findAll(Class<X> entityClass, final Criterion[] criterions, final Order[] orderBys, Page page) {
		return this.findAll(entityClass, criterions, orderBys, page.getCurrentRow(), page.getPageSize());
	}

	public <X> List<X> findAll(Class<X> entityClass, final Criterion[] criterions, final Order[] orderBys) {
		return this.findAll(entityClass, criterions, orderBys, -1, -1);
	}

	public List<T> findAll(String orderBy, boolean isAsc) {
		return findAll(entityClass, orderBy, isAsc);
	}

	@SuppressWarnings("unchecked")
	public <X> List<X> findAll(Class<X> entityClass) {
		return findByCriteria(DetachedCriteria.forClass(entityClass));
	}

	public List<T> findAll() {
		return findAll(entityClass);
	}

	public T findUnique(final Criterion... criterions) {
		return findUnique(entityClass, criterions);
	}

	public <X> X findUnique(Class<X> clazz, final Criterion... criterions) {
		Criteria executableCriteria = getSession().createCriteria(clazz);
		for (Criterion c : criterions) {
			executableCriteria.add(c);
		}
		@SuppressWarnings("unchecked")
		X result = (X) executableCriteria.uniqueResult();
		return result;
	}

	@SuppressWarnings("unchecked")

	public <X> List<X> find(DetachedCriteria detachedCriteria) {
		return findByCriteria(detachedCriteria);
	}

	@SuppressWarnings("unchecked")

	public List<T> find(long firstResult, long maxResults, final Criterion... criterions) {
		return findByCriteria(createDetachedCriteria(criterions), firstResult, maxResults);
	}

	@SuppressWarnings("unchecked")

	public List<T> find(DetachedCriteria detachedCriteria, long firstResult, long maxResults) {
		return findByCriteria(detachedCriteria, firstResult, maxResults);
	}

	@SuppressWarnings("unchecked")

	public List<T> find(Page page, final Criterion... criterions) {
		return findByCriteria(createDetachedCriteria(criterions), page.getCurrentRow(), page.getPageSize());
	}

	@SuppressWarnings("unchecked")

	public List<T> find(DetachedCriteria detachedCriteria, Page page, final Criterion... criterions) {
		return findByCriteria(createDetachedCriteria(detachedCriteria, criterions), page.getCurrentRow(),
				page.getPageSize());
	}

	public ListPage<T> findListPage(DetachedCriteria detachedCriteria, final Page page, final Criterion... criterions) {
		detachedCriteria = detachedCriteria == null ? createDetachedCriteria(criterions)
				: this.createDetachedCriteria(detachedCriteria, criterions);
		List<T> ls = this.find(detachedCriteria, page.getCurrentRow(), page.getPageSize());
		page.setRowCount(this.getDetachedCriteriaRowCount(detachedCriteria));
		return new ListPage<T>(ls, page);
	}

	public ListPage<T> findListPage(final Page page, final Criterion... criterions) {
		return findListPage(null, page, criterions);
	}

	public long getDetachedCriteriaRowCount(Class<?> clazz, final Criterion... criterions) {
		return getDetachedCriteriaRowCount(DetachedCriteria.forClass(clazz), criterions);
	}

	public long getDetachedCriteriaRowCount(final DetachedCriteria detachedCriteria, final Criterion... criterions) {
		this.createDetachedCriteria(detachedCriteria, criterions);
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		long totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		return totalCount;

	}

	public List<T> find(final Page page, T entity) {
		return find(null, page, Example.create(entity));
	}

	public ListPage<T> findListPage(final Page page, T entity) {
		return findListPage(null, page, Example.create(entity));
	}

	public long getDetachedCriteriaRowCount(final T entity) {
		return getDetachedCriteriaRowCount(entityClass, Example.create(entity));
	}

	protected Query getQuery(final String queryString, final long pageIndex, final long pageSize, final Object param,
			final long type, Session session, Class<?> entity) {
		Query query = null;
		if (type == 2) { // createQuery HQL
			query = session.createQuery(queryString);
		} else if (type == 1) { // createSQLQuery SQL
			if (queryString.toLowerCase().trim().startsWith("select count"))
				query = session.createSQLQuery(queryString);
			else
				query = session.createSQLQuery(queryString).addEntity(entity);
		}
		if (param != null) {
			if (param instanceof Map<?, ?>) {
				query.setProperties((Map<?, ?>) param);
			} else if (param.getClass().isArray()) {
				int len = Array.getLength(param);
				for (int i = 0; i < len; i++) {
					query.setParameter(i, Array.get(param, i));
				}
			} else {
				query.setProperties(param);
			}
		}
		setPageResult(query, pageIndex, pageSize); // 璁剧疆鍒嗛�??
		return query;
	}

	protected void setPageResult(Query query, long pageIndex, long pageSize) {
		if (pageIndex >= 0)
			query.setFirstResult((int) pageIndex);
		if (pageSize > 0)
			query.setMaxResults((int) pageSize);
	}

	protected DetachedCriteria createDetachedCriteria(final Criterion... criterions) {
		return createDetachedCriteria(entityClass, criterions);
	}

	@SuppressWarnings("rawtypes")
	protected DetachedCriteria createDetachedCriteria(final Class clazz, final Criterion... criterions) {
		return createDetachedCriteria(DetachedCriteria.forClass(clazz), criterions);
	}

	protected DetachedCriteria createDetachedCriteria(DetachedCriteria detachedCriteria,
			final Criterion... criterions) {

		if (detachedCriteria == null)
			detachedCriteria = DetachedCriteria.forClass(entityClass);
		if (criterions == null)
			return detachedCriteria;
		for (Criterion d : criterions) {
			detachedCriteria.add(d);
		}
		return detachedCriteria;
	}

	protected Criterion[] getCriterionLike(Map<String, ?> values) {
		if (values == null || values.isEmpty())
			return null;
		List<Criterion> list = new ArrayList<Criterion>();
		for (Entry<String, ?> m : values.entrySet()) {
			list.add(Restrictions.like(m.getKey(), m.getValue()));
		}
		return list.toArray(new Criterion[0]);
	}

	protected Criterion getBentweenCriterion(String propertyName, Object small, Object big) {
		if (small == null || big == null)
			return null;
		return Restrictions.between(propertyName, small, big);
	}

}
