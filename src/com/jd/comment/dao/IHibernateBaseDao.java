package com.jd.comment.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.jd.comment.util.ListPage;
import com.jd.comment.util.Page;

/**   
* Hibernate����dao�����ӿ�
* @date 2015��3��27�� ����8:46:24   
* @author Eric   
* @version V1.0   
*/
public interface IHibernateBaseDao<T, PK extends Serializable> { 
    
    //---------------------------�����ǻ�ȡ����Ľӿ�------------------------
    
    /**   
    * ����ID��ȡ����
    * @date 2015��3��28�� ����3:15:03   
    * @author Eric   
    * @param id ����Id
    * @return T ��ȡ�Ķ���
    */
    T get(final PK id);
    
    /**   
    * ���ݶ������ͺ�ID��ȡ����
    * @date 2015��3��28�� ����3:17:52   
    * @author Eric   
    * @param clazz Ҫ��ȡ�Ķ�������
    * @param id    Ҫ��ȡ�Ķ���Id
    * @return X    ��ȡ�Ķ���
    */
    <X> X get(Class<X> clazz, final PK id);
    
    /**   
    * ����ID��ȡ����   
    * @date 2015��3��30�� ����6:52:42   
    * @author Eric   
    * @param id Ҫ��ȡ�����ID
    * @return T ��ȡ�Ķ���  
    */
    T load(final PK id);
    
    /**   
     * ���ݶ������ͺ�ID��ȡ����
     * @date 2015��3��28�� ����3:17:52   
     * @author Eric   
     * @param clazz Ҫ��ȡ�Ķ�������
     * @param id    Ҫ��ȡ�Ķ���Id
     * @return X    ��ȡ�Ķ���
     */
    <X> X load(Class<X> clazz, final PK id);
   
    //---------------------------������ɾ������Ľӿ�------------------------
    
    /**   
    * ɾ��һ��ʵ�����
    * @date 2015��3��30�� ����6:54:26   
    * @author Eric   
    * @param   entity Ҫɾ����ʵ�����
    * @return  void  
    */
    void delete(final T entity) ;
  
    /**   
    * ����ɾ�������еĶ���
    * @date 2015��3��30�� ����6:55:55   
    * @author Eric   
    * @param entities ��ɾ���Ķ��󼯺�
    * @return void  
    */
    void deleteAll(final Collection<T> entities);

    /**   
    * ����IDɾ������ 
    * @date 2015��3��30�� ����6:57:10   
    * @author Eric   
    * @param  id ��ɾ�������ID
    * @return void  
    */
    void deleteById(final PK id);

    /**   
    * ����ID����ɾ������
    * @date 2015��3��30�� ����6:58:17   
    * @author Eric   
    * @param ids ��ɾ�������ID
    * @return void  
    */
    void deleteByIds(final PK [] ids) ;
    
    //---------------------------��������������¶���Ľӿ�------------------------
   
    /**   
    * ����һ��ʵ���¼
    * @date 2015��3��30�� ����7:03:51   
    * @author Eric   
    * @param entity ����ӵ�ʵ��
    * @return void  
    */
    void save(T entity) ;
  
    /**   
    * �������ʵ���¼
    * @date 2015��3��30�� ����7:04:40   
    * @author Eric   
    * @param entities ����ӵļ�¼����
    * @return void  
    */
    void saveAll(Collection<T> entities) ;
    
    /**   
    * ���������һ��ʵ���¼
    * @date 2015��3��30�� ����7:05:25   
    * @author Eric   
    * @param entity ����������µ�ʵ��
    * @return void  
    */
    void saveOrUpdate(final T entity) ;
    
    /**   
     * ���������һ��ʵ���¼
     * @date 2015��3��30�� ����7:05:25   
     * @author Eric   
     * @param entity ����������µ�ʵ��
     * @return void  
     */
    <X> void saveOrUpdate(Class<X> clazz,final X entity) ;
    /**   
    * �������������ʵ�� 
    * @date 2015��3��30�� ����7:05:59   
    * @author Eric   
    * @param entities ����������µ�ʵ��
    * @return void  
    */
    void saveOrUpdateAll(final Collection<T> entities) ;
    
    /**   
    * �־û�һ��ʵ���¼
    * @date 2015��3��30�� ����7:07:05   
    * @author Eric   
    * @param entity ���־û���ʵ��
    * @return void  
    */
    void persist(T entity) ;

    /**   
    * ����һ��ʵ���¼
    * @date 2015��3��30�� ����7:07:43   
    * @author Eric   
    * @param entity �����µ�ʵ��
    * @return void  
    */
    void update(T entity) ;

    /**   
    * ��������ʵ���¼
    * @date 2015��3��30�� ����7:09:28   
    * @author Eric   
    * @param entities �����µ�ʵ���¼
    * @return void  
    */
    void updateAll(Collection<T> entities) ;
    
    //---------------------------��������������ӿ�------------------------------
   
    /**   
    * ǿ�ƽ��д��ڴ浽���ݿ��ͬ��
    * @date 2015��3��30�� ����7:09:59   
    * @author Eric   
    * @return void  
    */
    void flush() ;
    
    /**   
    * ���Session���棬ִ��SQL
    * @date 2015��3��30�� ����7:11:37   
    * @author  Eric   
    * @reuturn void  
    */
    void clear() ;
    
    /**   
    * ����ʵ���¼ 
    * @date 2015��4��1�� ����9:19:31   
    * @author Eric   
    * @param entity �����µ�ʵ��
    * @return void  
    */
    void refresh(T entity);

    //------------------------------������ͨ��HQL����------------------------------------------------
    
    /**   
    * ʹ��HQL��ѯ������ҳ
    * @date 2015��4��1�� ����8:37:31   
    * @author Eric   
    * @param hql HSQL��ѯ���
    * @param page ��ҳ��Ϣ
    * @return ListPage<T>  ��ҳ���
    */
    ListPage<T> findHqlListPage(String hql, Page page);

    /**   
    * ʹ��HQL��ѯ������ҳ�Ͳ���
    * @date 2015��4��4�� ����2:18:48   
    * @author Eric   
    * @param hql  HQL��ѯ���
    * @param page ��ҳ��Ϣ
    * @param obj  ����
    * @return
    * ListPage<T>  ��ҳ���
    */
    ListPage<T> findHqlListPage(String hql, Page page, Object obj);

    /**   
    * ʹ��HQL��ѯ
    * @date 2015��4��4�� ����2:57:45   
    * @author Eric   
    * @param hql HQL��ѯ���
    * @return
    * List<T>  ��ѯ���
    */
    List<T> findHql(String hql);

    /**   
    * ʹ��HQL��ѯ�������� 
    * @date 2015��4��4�� ����2:58:12   
    * @author Eric   
    * @param hql HQL��ѯ���
    * @param obj ����
    * @return
    * List<T>  ��ѯ���
    */
    List<T> findHql(String hql, Object obj);

    /**   
    * ����HQL��ѯ������ҳ��Ϣ
    * @date 2015��4��4�� ����2:59:17   
    * @author Eric   
    * @param hql HQL��ѯ���
    * @param pageIndex ��ҳ��ʼҳ
    * @param pageSize  ��ҳ��С
    * @return  
    * List<T>   ��ѯ���
    */
    List<T> findHql(String hql,  long pageIndex,  long pageSize);

    /**   
    * ʹ��HQL��ѯ������ҳ�Ͳ���
    * @date 2015��4��4�� ����3:00:17   
    * @author Eric   
    * @param hql HQL��ѯ���
    * @param page ��ҳ
    * @param obj  ����
    * @return
    * List<T>  ��ѯ���
    */
    List<T> findHql(String hql, Page page, Object obj);

    /**   
    * ʹ��HQL��ѯ������ҳ��Ϣ�Ͳ���
    * @date 2015��4��4�� ����3:01:54   
    * @author Eric   
    * @param hql HQL��ѯ���
    * @param pageIndex ��ҳ��ʵҳ
    * @param pageSize  ��ҳ��С
    * @param obj       ����
    * @return 
    * List<T>  ��ѯ��� 
    */
    List<T> findHql(String hql,  long pageIndex,  long pageSize, Object obj);

    /**   
    * ����HQL��ѯͳ������
    * @date 2015��4��4�� ����3:02:56   
    * @author Eric   
    * @param hql HQL��ѯ���
    * @return
    * long  ��ѯ���������
    */
    long getHqlRowCount(String hql);

    /**   
    * ����HQL��ѯͳ������,������
    * @date 2015��4��4�� ����3:03:57   
    * @author Eric   
    * @param hql HQL��ѯ���
    * @param obj ����
    * @return
    * long  ��ѯ�������
    */
    long getHqlRowCount(String hql, Object obj);

    
    
    //------------------------------������QBC����------------------------------------------------
  
    /**   
    * QBC��ѯ������ҳ
    * @date 2015��4��4�� ����3:10:16   
    * @author Eric   
    * @param criteria ��ѯ����
    * @param firstResult ��ҳ��ʼλ��
    * @param maxResults  ��ҳ��С
    * @return
    * List   ��ѯ���
    */
    @SuppressWarnings("rawtypes")
    List findByCriteria(DetachedCriteria criteria,  long firstResult,  long maxResults);

    /**   
    * QBC��ѯ 
    * @date 2015��4��4�� ����3:10:55   
    * @author Eric   
    * @param criteria ��ѯ����
    * @return
    * List  ��ѯ���
    */
    @SuppressWarnings("rawtypes")
    List findByCriteria(DetachedCriteria criteria);

    /**   
    * QBC��ʽ��ѯ��ȡȫ�����󣬴������ҳ
    * @date 2015��4��4�� ����2:28:08   
    * @author Eric   
    * @param entityClass Ҫ��ȡ�Ķ�������
    * @param criterions  ��ѯ����
    * @param orderBys    ����ʽ
    * @param firstResult ��ҳ��ʼλ��
    * @param maxResults  ��ȡ����������
    * @return
    * List<X>   ��ѯ���Ķ����б�
    */
    <X> List<X> findAll(Class<X> entityClass, Criterion[] criterions, Order[] orderBys,  long firstResult,  long maxResults);

    /**   
    * QBC��ȡȫ������,�������ֶ����������
    * @date 2015��4��4�� ����2:31:22   
    * @author Eric   
    * @param entityClass Ҫ��ö��������
    * @param orderBy     �����ֶ���
    * @param isAsc       �Ƿ�������
    * @return
    * List<X>  ��ȡ���Ķ����б�
    */ 
    <X> List<X> findAll(Class<X> entityClass, String orderBy, boolean isAsc);
   
    /**   
    * QBC��ȡȫ�����󣬴���ѯ������������������ҳ
    * @date 2015��4��4�� ����3:11:51   
    * @author Eric   
    * @param entityClass Ҫ��ȡ�Ķ���������Ϣ
    * @param criterions  ��ѯ����
    * @param orderBys    ��������
    * @param page        ��ҳ
    * @return
    * List<X>  ��ѯ���
    */
    <X> List<X> findAll(Class<X> entityClass, Criterion[] criterions, Order[] orderBys, Page page);

    /**   
    * QBC��ȡȫ�����󣬴���ѯ����������������������ҳ
    * @date 2015��4��4�� ����3:13:25   
    * @author Eric   
    * @param entityClass Ҫ��ȡ�Ķ���������Ϣ
    * @param criterions  ��ѯ����
    * @param orderBys    ��������
    * @return 
    * List<X>  ��ѯ��� 
    */
    <X> List<X> findAll(Class<X> entityClass, Criterion[] criterions, Order[] orderBys);

    /**   
    * QBC��ȡȫ������,�������ֶκ����������
    * @date 2015��4��4�� ����3:14:13   
    * @author Eric   
    * @param orderBy �����ֶ���
    * @param isAsc   �Ƿ�������
    * @return
    * List<T>  ��ѯ���
    */
    List<T> findAll(String orderBy, boolean isAsc);
    
    /**   
    * QBC��ȡȫ������ 
    * @date 2015��4��4�� ����3:15:34   
    * @author Eric   
    * @param entityClass Ҫ��ȡ�����������Ϣ
    * @return
    * List<X>  ��ѯ���
    */
    <X> List<X> findAll(Class<X> entityClass);

    /**   
    * QBC��ȡȫ������
    * @date 2015��4��4�� ����3:16:10   
    * @author Eric   
    * @return
    * List<T>  ��ѯ���
    */
    List<T> findAll();

    /**   
    * QBC���ݲ�ѯ������ȡΨһ����
    * @date 2015��4��4�� ����3:16:37   
    * @author Eric   
    * @param criterions ��ѯ����
    * @return 
    * T  ��ȡ�Ķ���
    */
    T findUnique(Criterion... criterions);

    /**   
    * QBC���ݲ�ѯ������ȡΨһ����
    * @date 2015��4��4�� ����3:17:24   
    * @author Eric   
    * @param clazz  Ҫ��ȡ�Ķ���������Ϣ
    * @param criterions ��ѯ����
    * @return
    * X  ��ȡ�Ķ���
    */
    <X> X findUnique(Class<X> clazz, Criterion... criterions);

    /**
     * ����������ȡ����
     * 
     * @param detachedCriteria
     *            hibernate ���߲�ѯ DetachedCriteria
     */
    /**   
    * QBC��ѯ������������ȡ���� 
    * @date 2015��4��4�� ����3:18:09   
    * @author Eric   
    * @param detachedCriteria ��ѯ����
    * @return
    * List<X>  ��ѯ���
    */
    <X> List<X> find(DetachedCriteria detachedCriteria);

    /**   
    * QBC��ѯ������������ȡ����
    * @date 2015��4��4�� ����3:22:10   
    * @author Eric   
    * @param firstResult �ڼ�����ʼ��ѯ
    * @param maxResults  ��󷵻���������
    * @param criterions  �����ɱ��Criterion(��������ѯ����)
    * @return
    * List<T>  ��ѯ���
    */
    List<T> find( long firstResult,  long maxResults, Criterion... criterions);
    
    /**   
    * QBC��ѯ������������ȡ����
    * @date 2015��4��4�� ����3:28:40   
    * @author Eric   
    * @param detachedCriteria ��ѯ����
    * @param firstResult      �ӵڼ�����ʼ��ѯ
    * @param maxResults       ��󷵻���������
    * @return
    * List<T>  ��ѯ���
    */
    List<T> find(DetachedCriteria detachedCriteria,  long firstResult,  long maxResults);

    /**   
    * QBC��ѯ,����������ȡ����,����ҳ
    * <p>��ϸ˵����һ��<br>    
    * ��ϸ˵���ڶ��� 
    * @date 2015��4��4�� ����3:29:44   
    * @author Eric   
    * @param page  ��ҳ��Ϣ
    * @param criterions  �����ɱ��Criterion(��������ѯ����)
    * @return
    * List<T>  ��ѯ���
    */
    List<T> find(Page page, Criterion... criterions);

    /**   
    * QBC��ѯ,����������ȡ����,����ҳ 
    * @date 2015��4��4�� ����3:33:34   
    * @author Eric   
    * @param detachedCriteria hibernate ���߲�ѯ DetachedCriteria
    * @param page ��ҳ��Ϣ
    * @param criterions �����ɱ��Criterion(��������ѯ����)
    * @return
    * List<T>  ��ѯ���
    */
    List<T> find(DetachedCriteria detachedCriteria, Page page, Criterion... criterions);

    /**   
    * QBC��ѯ������������ȡ����,����ҳ,���ܼ�¼��
    * @date 2015��4��4�� ����3:34:26   
    * @author Eric   
    * @param detachedCriteria ���߲�ѯ DetachedCriteria
    * @param page ��ҳ��Ϣ
    * @param criterions �����ɱ��Criterion(��������ѯ����)
    * @return
    * ListPage<T>  ��ҳ���
    */
    ListPage<T> findListPage(DetachedCriteria detachedCriteria, Page page, Criterion... criterions);

    /**   
    * QBC��ѯ������������ȡ����,����ҳ,���ܼ�¼��
    * @date 2015��4��4�� ����3:35:29   
    * @author Eric   
    * @param page ��ҳ��Ϣ
    * @param criterions �����ɱ��Criterion(��������ѯ����)
    * @return
    * ListPage<T>  ��ҳ���
    */
    ListPage<T> findListPage(Page page, Criterion... criterions);
    
    /**   
    * QBC��ѯ��ͳ�Ƽ�¼����
    * @date 2015��4��4�� ����3:41:40   
    * @author Eric   
    * @param clazz Ҫͳ�ƵĶ����������Ϣ
    * @param criterions ��ѯ����
    * @return
    * long  ��¼����
    */
    long getDetachedCriteriaRowCount(Class<?> clazz, Criterion... criterions);

    /**   
    * QBC��ѯ��ͳ�Ƽ�¼����
    * @date 2015��4��4�� ����3:40:20   
    * @author Eric   
    * @param detachedCriteria ���߲�ѯ DetachedCriteria
    * @param criterions ��ѯ����
    * @return
    * long  ��¼����
    */
    long getDetachedCriteriaRowCount(DetachedCriteria detachedCriteria, Criterion... criterions);

    
    //------------------------------������QBE����------------------------------------------------
   
    /**   
    * QBE��ѯ������������ȡ����,����ҳ,���ܼ�¼�� 
    * @date 2015��4��4�� ����3:37:13   
    * @author Eric   
    * @param page ��ҳ��Ϣ
    * @param entity ��ѯ��example
    * @return
    * ListPage<T>  ��ҳ���
    */
    ListPage<T> findListPage(Page page, T entity);

    /**   
    * QBE��ѯ������������ȡ����,����ҳ
    * @date 2015��4��4�� ����3:37:57   
    * @author Eric   
    * @param page ��ҳ��Ϣ
    * @param entity ��ѯ��example
    * @return
    * List<T> ��ѯ���
    */
    List<T> find(Page page, T entity);

    /**   
    * QBE��ѯͳ�Ƽ�¼����
    * @date 2015��4��4�� ����3:38:41   
    * @author Eric   
    * @param entity ��ѯ��example
    * @return
    * long  ��¼���� 
    */
    long getDetachedCriteriaRowCount(T entity);

}