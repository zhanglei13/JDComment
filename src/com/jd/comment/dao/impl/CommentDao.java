package com.jd.comment.dao.impl;

import com.jd.comment.dao.ICommentDao;
import com.jd.comment.entity.Comment;
import com.jd.comment.entity.User;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Ye on 2016/7/27.
 */
@Repository("commentDao")
public class CommentDao extends HibernateBaseDao<Comment, Long> implements ICommentDao {

	@Override
	public List<Comment> getCommentList(Long productId) {
		DetachedCriteria criteria =DetachedCriteria.forClass(Comment.class);
		criteria.add(Restrictions.eq("productId", productId));
		List l = this.findByCriteria(criteria);
		return l;
	}

	/*
	 * insert an user comment
	 * to do
	 */
	@Override
	public String insertComment(User user, Comment comment) {
		if(user == null){
			return "没有这个用户！";
		}
		comment.setUserId(user.getUserId());
		comment.setCommentTime(new Date());
		this.save(comment);
		return null;
	}

	@Override
	public String appendComment(User user, Comment comment) {
		// TODO Auto-generated method stub
		if(user == null){
			return "没有这个用户！";
		}
		comment.setUserId(user.getUserId());
		comment.setCommentTime(new Date());
		this.save(comment);
		return null;
	}

	@Override
	public int queryCount(Long productId) {
		// TODO Auto-generated method stub
		return 0;
	}
    
}
