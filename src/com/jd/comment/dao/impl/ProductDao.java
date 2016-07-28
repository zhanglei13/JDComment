package com.jd.comment.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jd.comment.dao.IProductDao;
import com.jd.comment.entity.Product;

@Repository("productDao")
public class ProductDao extends HibernateBaseDao<Product, Long>implements IProductDao {

	protected static final Logger LOGGER = Logger.getLogger(ProductDao.class);

	// 查询商品详情页
	@Override
	public List<Product> inqure() {
		return this.findAll(Product.class);
	}

	// 根据商品id查询商品详情页
	@Override
	 public Product inqure(Long id) {
		return this.get(id);
	}
	
	@Override
	public Product get(Long productId) {
		Criterion criterions = Restrictions.eq("productId", productId);
		return this.findUnique(criterions);
	}
}
