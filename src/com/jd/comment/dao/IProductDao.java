package com.jd.comment.dao;

import java.util.List;
import com.jd.comment.entity.Product;

public interface IProductDao {
	// 查询商品详情
	List<Product> inqure();

	// 根据商品id查询商品详情
	Product inqure(Long id);
	
	Product get(Long productId);
}
