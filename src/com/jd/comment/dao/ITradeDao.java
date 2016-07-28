package com.jd.comment.dao;

import java.util.List;

import com.jd.comment.entity.Trade;

public interface ITradeDao {
	//根据用户id查询商品id和是否已经评价
	List<Trade>query(Long userId);
	
	List<Trade> list(Long userId);	
	
	void updatestate(Long productId,Long userId);
		
		List<Trade> getTradeInformation(Long userId);
}
