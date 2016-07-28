package com.jd.comment.service;

import java.util.List;

import com.jd.comment.model.ANotDetail;
import com.jd.comment.model.TradeDetail;

public interface ITradeService {
	List<TradeDetail> query(Long userId);

	List<ANotDetail> listAleadyComment(String username);

	List<ANotDetail> listNotAleadyComment(String username);
	
	void updatestate(Long productId,Long userId);
}
