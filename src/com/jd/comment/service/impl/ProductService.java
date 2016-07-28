package com.jd.comment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.comment.dao.ICommentDao;
import com.jd.comment.dao.IProductDao;
import com.jd.comment.entity.Product;
import com.jd.comment.model.ProductDetail;
import com.jd.comment.service.IProductService;

@Service("productService")
public class ProductService implements IProductService {
	protected static Logger logger = Logger.getLogger(ProductService.class);

	@Autowired
	protected IProductDao productDao;

	@Autowired
	protected ICommentDao commentDao;

	@Override
	public List<ProductDetail> inqure() {
		List<Product> products = productDao.inqure();
		List<ProductDetail> result = new ArrayList<>();

		for (Product p : products) {
			ProductDetail detail = new ProductDetail();
			detail.setProductId(p.getProductId());
			detail.setProductImg(p.getProductImg());
			detail.setProductName(p.getProductName());
			detail.setProductNo(p.getProductNo());
			detail.setProductNum(p.getProductNum());
			detail.setProductPrice(p.getProductPrice());
			detail.setCount(commentDao.queryCount(p.getProductId()));
			result.add(detail);
		}

		return result;
	}

	@Override
	public Product inqure(Long id) {
		return productDao.inqure(id);
	}

}
