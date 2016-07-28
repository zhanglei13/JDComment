package com.jd.comment.service;

import java.util.List;

import com.jd.comment.entity.Product;
import com.jd.comment.model.ProductDetail;

public interface IProductService {
	List<ProductDetail> inqure();
	
	Product inqure(Long id);
}
