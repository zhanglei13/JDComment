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
* Hibernate基本dao操作接口
* @date 2015年3月27日 下午8:46:24   
* @author Eric   
* @version V1.0   
*/
public interface IHibernateBaseDao<T, PK extends Serializable> { 
    
    //---------------------------以下是获取对象的接口------------------------
    
    /**   
    * 根据ID获取对象
    * @date 2015年3月28日 下午3:15:03   
    * @author Eric   
    * @param id 对象Id
    * @return T 获取的对象
    */
    T get(final PK id);
    
    /**   
    * 根据对象类型和ID获取对象
    * @date 2015年3月28日 下午3:17:52   
    * @author Eric   
    * @param clazz 要获取的对象类型
    * @param id    要获取的对象Id
    * @return X    获取的对象
    */
    <X> X get(Class<X> clazz, final PK id);
    
    /**   
    * 根据ID获取对象   
    * @date 2015年3月30日 下午6:52:42   
    * @author Eric   
    * @param id 要获取对象的ID
    * @return T 获取的对象  
    */
    T load(final PK id);
    
    /**   
     * 根据对象类型和ID获取对象
     * @date 2015年3月28日 下午3:17:52   
     * @author Eric   
     * @param clazz 要获取的对象类型
     * @param id    要获取的对象Id
     * @return X    获取的对象
     */
    <X> X load(Class<X> clazz, final PK id);
   
    //---------------------------以下是删除对象的接口------------------------
    
    /**   
    * 删除一个实体对象
    * @date 2015年3月30日 下午6:54:26   
    * @author Eric   
    * @param   entity 要删除的实体对象
    * @return  void  
    */
    void delete(final T entity) ;
  
    /**   
    * 批量删除集合中的对象
    * @date 2015年3月30日 下午6:55:55   
    * @author Eric   
    * @param entities 待删除的对象集合
    * @return void  
    */
    void deleteAll(final Collection<T> entities);

    /**   
    * 根据ID删除对象 
    * @date 2015年3月30日 下午6:57:10   
    * @author Eric   
    * @param  id 待删除对象的ID
    * @return void  
    */
    void deleteById(final PK id);

    /**   
    * 根据ID批量删除对象
    * @date 2015年3月30日 下午6:58:17   
    * @author Eric   
    * @param ids 待删除对象的ID
    * @return void  
    */
    void deleteByIds(final PK [] ids) ;
    
    //---------------------------以下是新增或更新对象的接口------------------------
   
    /**   
    * 新增一个实体记录
    * @date 2015年3月30日 下午7:03:51   
    * @author Eric   
    * @param entity 待添加的实体
    * @return void  
    */
    void save(T entity) ;
  
    /**   
    * 批量添加实体记录
    * @date 2015年3月30日 下午7:04:40   
    * @author Eric   
    * @param entities 待添加的记录集合
    * @return void  
    */
    void saveAll(Collection<T> entities) ;
    
    /**   
    * 新增或更新一个实体记录
    * @date 2015年3月30日 下午7:05:25   
    * @author Eric   
    * @param entity 待新增或更新的实体
    * @return void  
    */
    void saveOrUpdate(final T entity) ;
    
    /**   
     * 新增或更新一个实体记录
     * @date 2015年3月30日 下午7:05:25   
     * @author Eric   
     * @param entity 待新增或更新的实体
     * @return void  
     */
    <X> void saveOrUpdate(Class<X> clazz,final X entity) ;
    /**   
    * 批量新增或更新实体 
    * @date 2015年3月30日 下午7:05:59   
    * @author Eric   
    * @param entities 待新增或更新的实体
    * @return void  
    */
    void saveOrUpdateAll(final Collection<T> entities) ;
    
    /**   
    * 持久化一个实体记录
    * @date 2015年3月30日 下午7:07:05   
    * @author Eric   
    * @param entity 待持久化的实体
    * @return void  
    */
    void persist(T entity) ;

    /**   
    * 更新一个实体记录
    * @date 2015年3月30日 下午7:07:43   
    * @author Eric   
    * @param entity 待更新的实体
    * @return void  
    */
    void update(T entity) ;

    /**   
    * 批量更新实体记录
    * @date 2015年3月30日 下午7:09:28   
    * @author Eric   
    * @param entities 待更新的实体记录
    * @return void  
    */
    void updateAll(Collection<T> entities) ;
    
    //---------------------------以下是清理操作接口------------------------------
   
    /**   
    * 强制进行从内存到数据库的同步
    * @date 2015年3月30日 下午7:09:59   
    * @author Eric   
    * @return void  
    */
    void flush() ;
    
    /**   
    * 清除Session缓存，执行SQL
    * @date 2015年3月30日 下午7:11:37   
    * @author  Eric   
    * @reuturn void  
    */
    void clear() ;
    
    /**   
    * 更新实体记录 
    * @date 2015年4月1日 上午9:19:31   
    * @author Eric   
    * @param entity 待更新的实体
    * @return void  
    */
    void refresh(T entity);

    //------------------------------以下是通过HQL操作------------------------------------------------
    
    /**   
    * 使用HQL查询，带分页
    * @date 2015年4月1日 下午8:37:31   
    * @author Eric   
    * @param hql HSQL查询语句
    * @param page 分页信息
    * @return ListPage<T>  分页结果
    */
    ListPage<T> findHqlListPage(String hql, Page page);

    /**   
    * 使用HQL查询，带分页和参数
    * @date 2015年4月4日 下午2:18:48   
    * @author Eric   
    * @param hql  HQL查询语句
    * @param page 分页信息
    * @param obj  参数
    * @return
    * ListPage<T>  分页结果
    */
    ListPage<T> findHqlListPage(String hql, Page page, Object obj);

    /**   
    * 使用HQL查询
    * @date 2015年4月4日 下午2:57:45   
    * @author Eric   
    * @param hql HQL查询语句
    * @return
    * List<T>  查询结果
    */
    List<T> findHql(String hql);

    /**   
    * 使用HQL查询，带参数 
    * @date 2015年4月4日 下午2:58:12   
    * @author Eric   
    * @param hql HQL查询语句
    * @param obj 参数
    * @return
    * List<T>  查询结果
    */
    List<T> findHql(String hql, Object obj);

    /**   
    * 根据HQL查询，带分页信息
    * @date 2015年4月4日 下午2:59:17   
    * @author Eric   
    * @param hql HQL查询语句
    * @param pageIndex 分页起始页
    * @param pageSize  分页大小
    * @return  
    * List<T>   查询结果
    */
    List<T> findHql(String hql,  long pageIndex,  long pageSize);

    /**   
    * 使用HQL查询，带分页和参数
    * @date 2015年4月4日 下午3:00:17   
    * @author Eric   
    * @param hql HQL查询语句
    * @param page 分页
    * @param obj  参数
    * @return
    * List<T>  查询结果
    */
    List<T> findHql(String hql, Page page, Object obj);

    /**   
    * 使用HQL查询，带分页信息和参数
    * @date 2015年4月4日 下午3:01:54   
    * @author Eric   
    * @param hql HQL查询语句
    * @param pageIndex 分页其实页
    * @param pageSize  分页大小
    * @param obj       参数
    * @return 
    * List<T>  查询结果 
    */
    List<T> findHql(String hql,  long pageIndex,  long pageSize, Object obj);

    /**   
    * 根据HQL查询统计数量
    * @date 2015年4月4日 下午3:02:56   
    * @author Eric   
    * @param hql HQL查询语句
    * @return
    * long  查询结果的数量
    */
    long getHqlRowCount(String hql);

    /**   
    * 根据HQL查询统计数量,带参数
    * @date 2015年4月4日 下午3:03:57   
    * @author Eric   
    * @param hql HQL查询语句
    * @param obj 参数
    * @return
    * long  查询结果数量
    */
    long getHqlRowCount(String hql, Object obj);

    
    
    //------------------------------以下是QBC操作------------------------------------------------
  
    /**   
    * QBC查询，带分页
    * @date 2015年4月4日 下午3:10:16   
    * @author Eric   
    * @param criteria 查询条件
    * @param firstResult 分页起始位置
    * @param maxResults  分页大小
    * @return
    * List   查询结果
    */
    @SuppressWarnings("rawtypes")
    List findByCriteria(DetachedCriteria criteria,  long firstResult,  long maxResults);

    /**   
    * QBC查询 
    * @date 2015年4月4日 下午3:10:55   
    * @author Eric   
    * @param criteria 查询条件
    * @return
    * List  查询结果
    */
    @SuppressWarnings("rawtypes")
    List findByCriteria(DetachedCriteria criteria);

    /**   
    * QBC方式查询获取全部对象，带排序分页
    * @date 2015年4月4日 下午2:28:08   
    * @author Eric   
    * @param entityClass 要获取的对象类型
    * @param criterions  查询条件
    * @param orderBys    排序方式
    * @param firstResult 分页起始位置
    * @param maxResults  获取的最大对象数
    * @return
    * List<X>   查询到的对象列表
    */
    <X> List<X> findAll(Class<X> entityClass, Criterion[] criterions, Order[] orderBys,  long firstResult,  long maxResults);

    /**   
    * QBC获取全部对象,带排序字段与升降序参
    * @date 2015年4月4日 下午2:31:22   
    * @author Eric   
    * @param entityClass 要获得对象的类型
    * @param orderBy     排序字段名
    * @param isAsc       是否是正序
    * @return
    * List<X>  获取到的对象列表
    */ 
    <X> List<X> findAll(Class<X> entityClass, String orderBy, boolean isAsc);
   
    /**   
    * QBC获取全部对象，带查询条件，排序条件，分页
    * @date 2015年4月4日 下午3:11:51   
    * @author Eric   
    * @param entityClass 要获取的对象类型信息
    * @param criterions  查询条件
    * @param orderBys    排序条件
    * @param page        分页
    * @return
    * List<X>  查询结果
    */
    <X> List<X> findAll(Class<X> entityClass, Criterion[] criterions, Order[] orderBys, Page page);

    /**   
    * QBC获取全部对象，带查询条件，排序条件，不带分页
    * @date 2015年4月4日 下午3:13:25   
    * @author Eric   
    * @param entityClass 要获取的对象类型信息
    * @param criterions  查询条件
    * @param orderBys    排序条件
    * @return 
    * List<X>  查询结果 
    */
    <X> List<X> findAll(Class<X> entityClass, Criterion[] criterions, Order[] orderBys);

    /**   
    * QBC获取全部对象,带排序字段和升降序参数
    * @date 2015年4月4日 下午3:14:13   
    * @author Eric   
    * @param orderBy 排序字段名
    * @param isAsc   是否是正序
    * @return
    * List<T>  查询结果
    */
    List<T> findAll(String orderBy, boolean isAsc);
    
    /**   
    * QBC获取全部对象 
    * @date 2015年4月4日 下午3:15:34   
    * @author Eric   
    * @param entityClass 要获取对象的类型信息
    * @return
    * List<X>  查询结果
    */
    <X> List<X> findAll(Class<X> entityClass);

    /**   
    * QBC获取全部对象
    * @date 2015年4月4日 下午3:16:10   
    * @author Eric   
    * @return
    * List<T>  查询结果
    */
    List<T> findAll();

    /**   
    * QBC根据查询条件获取唯一对象
    * @date 2015年4月4日 下午3:16:37   
    * @author Eric   
    * @param criterions 查询条件
    * @return 
    * T  获取的对象
    */
    T findUnique(Criterion... criterions);

    /**   
    * QBC根据查询条件获取唯一对象
    * @date 2015年4月4日 下午3:17:24   
    * @author Eric   
    * @param clazz  要获取的对象类型信息
    * @param criterions 查询条件
    * @return
    * X  获取的对象
    */
    <X> X findUnique(Class<X> clazz, Criterion... criterions);

    /**
     * 根据条件获取数据
     * 
     * @param detachedCriteria
     *            hibernate 离线查询 DetachedCriteria
     */
    /**   
    * QBC查询，根据条件获取数据 
    * @date 2015年4月4日 下午3:18:09   
    * @author Eric   
    * @param detachedCriteria 查询条件
    * @return
    * List<X>  查询结果
    */
    <X> List<X> find(DetachedCriteria detachedCriteria);

    /**   
    * QBC查询，根据条件获取数据
    * @date 2015年4月4日 下午3:22:10   
    * @author Eric   
    * @param firstResult 第几条开始查询
    * @param maxResults  最大返回数据行数
    * @param criterions  数量可变的Criterion(面向对象查询条件)
    * @return
    * List<T>  查询结果
    */
    List<T> find( long firstResult,  long maxResults, Criterion... criterions);
    
    /**   
    * QBC查询，根据条件获取数据
    * @date 2015年4月4日 下午3:28:40   
    * @author Eric   
    * @param detachedCriteria 查询条件
    * @param firstResult      从第几条开始查询
    * @param maxResults       最大返回数据行数
    * @return
    * List<T>  查询结果
    */
    List<T> find(DetachedCriteria detachedCriteria,  long firstResult,  long maxResults);

    /**   
    * QBC查询,根据条件获取数据,带分页
    * <p>详细说明第一行<br>    
    * 详细说明第二行 
    * @date 2015年4月4日 下午3:29:44   
    * @author Eric   
    * @param page  分页信息
    * @param criterions  数量可变的Criterion(面向对象查询条件)
    * @return
    * List<T>  查询结果
    */
    List<T> find(Page page, Criterion... criterions);

    /**   
    * QBC查询,根据条件获取数据,带分页 
    * @date 2015年4月4日 下午3:33:34   
    * @author Eric   
    * @param detachedCriteria hibernate 离线查询 DetachedCriteria
    * @param page 分页信息
    * @param criterions 数量可变的Criterion(面向对象查询条件)
    * @return
    * List<T>  查询结果
    */
    List<T> find(DetachedCriteria detachedCriteria, Page page, Criterion... criterions);

    /**   
    * QBC查询，根据条件获取数据,带分页,有总记录数
    * @date 2015年4月4日 下午3:34:26   
    * @author Eric   
    * @param detachedCriteria 离线查询 DetachedCriteria
    * @param page 分页信息
    * @param criterions 数量可变的Criterion(面向对象查询条件)
    * @return
    * ListPage<T>  分页结果
    */
    ListPage<T> findListPage(DetachedCriteria detachedCriteria, Page page, Criterion... criterions);

    /**   
    * QBC查询，根据条件获取数据,带分页,有总记录数
    * @date 2015年4月4日 下午3:35:29   
    * @author Eric   
    * @param page 分页信息
    * @param criterions 数量可变的Criterion(面向对象查询条件)
    * @return
    * ListPage<T>  分页结果
    */
    ListPage<T> findListPage(Page page, Criterion... criterions);
    
    /**   
    * QBC查询，统计记录行数
    * @date 2015年4月4日 下午3:41:40   
    * @author Eric   
    * @param clazz 要统计的对象的类型信息
    * @param criterions 查询条件
    * @return
    * long  记录行数
    */
    long getDetachedCriteriaRowCount(Class<?> clazz, Criterion... criterions);

    /**   
    * QBC查询，统计记录行数
    * @date 2015年4月4日 下午3:40:20   
    * @author Eric   
    * @param detachedCriteria 离线查询 DetachedCriteria
    * @param criterions 查询条件
    * @return
    * long  记录行数
    */
    long getDetachedCriteriaRowCount(DetachedCriteria detachedCriteria, Criterion... criterions);

    
    //------------------------------以下是QBE操作------------------------------------------------
   
    /**   
    * QBE查询，根据条件获取数据,带分页,有总记录数 
    * @date 2015年4月4日 下午3:37:13   
    * @author Eric   
    * @param page 分页信息
    * @param entity 查询的example
    * @return
    * ListPage<T>  分页结果
    */
    ListPage<T> findListPage(Page page, T entity);

    /**   
    * QBE查询，根据条件获取数据,带分页
    * @date 2015年4月4日 下午3:37:57   
    * @author Eric   
    * @param page 分页信息
    * @param entity 查询的example
    * @return
    * List<T> 查询结果
    */
    List<T> find(Page page, T entity);

    /**   
    * QBE查询统计记录行数
    * @date 2015年4月4日 下午3:38:41   
    * @author Eric   
    * @param entity 查询的example
    * @return
    * long  记录行数 
    */
    long getDetachedCriteriaRowCount(T entity);

}