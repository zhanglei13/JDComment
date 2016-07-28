package com.jd.comment.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jd.comment.dao.ITradeDao;
import com.jd.comment.entity.Comment;
import com.jd.comment.entity.Trade;
@Repository("tradeDao")
public class TradeDao extends HibernateBaseDao<Trade, Long>implements ITradeDao {
	protected static final Logger LOGGER = Logger.getLogger(TradeDao.class);

	@Override
	public List<Trade> query(Long userId) {
		return this.findAll(Trade.class);
	}

	@Override
	public List<Trade> list(Long userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Trade.class)
				.add(Restrictions.eq("userId", userId));
		return this.findByCriteria(criteria);
	}

	@Override
	public void updatestate(Long productId, Long userId) {
		Criterion c1 = Restrictions.eq("productId", productId);
		Criterion c2 = Restrictions.eq("userId", userId);
		Trade t = this.findUnique(c1,c2);
		t.setTradeIsComment(0);
		this.saveOrUpdate(t);
	}

	@Override
	public List<Trade> getTradeInformation(Long userId) {
		return this.list(userId);
	}

}
