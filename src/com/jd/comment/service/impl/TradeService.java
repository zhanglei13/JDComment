package com.jd.comment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.comment.dao.IProductDao;
import com.jd.comment.dao.ITradeDao;
import com.jd.comment.dao.IUserDao;
import com.jd.comment.entity.Product;
import com.jd.comment.entity.Trade;
import com.jd.comment.entity.User;
import com.jd.comment.model.ANotDetail;
import com.jd.comment.model.TradeDetail;
import com.jd.comment.service.ITradeService;

@Service("tradeService")
public class TradeService implements ITradeService{

	protected static Logger logger = Logger.getLogger(TradeService.class);
	@Autowired
    protected ITradeDao tradeDao;
	
	@Autowired
    protected IUserDao userDao;
	
	@Autowired
    protected IProductDao productDao;

	@Override
	public List<TradeDetail> query(Long userId) {
		List<Trade> trades = tradeDao.query(userId);
		List<TradeDetail> result = new ArrayList<>();

		for (Trade t : trades) {
			TradeDetail detail = new TradeDetail();
			detail.setProductId(t.getProductId());
			detail.setTradeId(t.getTradeId());
			detail.setTradeIsComment(t.getTradeIsComment());
			detail.setTradeNum(t.getTradeNum());
			detail.setTradeTime(t.getTradeTime());
			detail.setUserId(t.getUserId());
			result.add(detail);
		}
		return result;
	}

	public List<Trade> getTrades(Long userId) {
		List<Trade> trades = tradeDao.list(userId);
		return trades;
	}
	
	@Override
	public List<ANotDetail> listAleadyComment(String username) {
		List<ANotDetail> result = new ArrayList<>();
		User user = userDao.getUser(username);
		List<Trade> trades = getTrades(user.getUserId());

		for(Trade t : trades) {
			if(t.getTradeIsComment() == 1) {
				ANotDetail detail = new ANotDetail();
				detail.setUserId(user.getUserId());
				Long productId = t.getProductId();
				Product p = productDao.get(productId);
				detail.setProductImg(p.getProductImg());
				detail.setProductName(p.getProductName());
				detail.setProductId(p.getProductId().toString());
				result.add(detail);
			}
		}
		return result;
	}
	
	@Override
	public List<ANotDetail> listNotAleadyComment(String username) {
		List<ANotDetail> result = new ArrayList<>();
		User user = userDao.getUser(username);
		List<Trade> trades = getTrades(user.getUserId());
		for(Trade t : trades) {
			if(t.getTradeIsComment() == 0) {
				ANotDetail detail = new ANotDetail();
				detail.setUserId(user.getUserId());
				Long productId = t.getProductId();
				Product p = productDao.get(productId);
				detail.setProductImg(p.getProductImg());
				detail.setProductName(p.getProductName());
				detail.setProductId(p.getProductId().toString());
				result.add(detail);
			}
		}
		return result;
	}

	@Override
	public void updatestate(Long productId, Long userId) {
		tradeDao.updatestate(productId, userId);
	}

}
