package com.jd.comment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.comment.dao.ICommentDao;
import com.jd.comment.dao.IUserDao;
import com.jd.comment.entity.Comment;
import com.jd.comment.entity.User;
import com.jd.comment.service.ICommentService;

@Service("commentService")
public class CommentService implements ICommentService {

	protected static Logger logger = Logger.getLogger(CommentService.class);

	@Autowired
	protected ICommentDao commentDao;

	@Override
	public List<Comment> getCommentList(Long productId) {
		return commentDao.getCommentList(productId);
	}

	@Override
	public String insertComment(User user, Comment comment) {
		return commentDao.insertComment(user, comment);
	}

	@Override
	public String appendComment(User user, Comment comment) {
		return commentDao.appendComment(user, comment);
	}

	@Override
	public List<Comment> getAllComments(Long productId) {
		List<Comment> comments = commentDao.getCommentList(productId);
		return comments;
	}

	@Override
	public List<Comment> getGoodComments(Long productId) {
		List<Comment> result = new ArrayList<>();
		List<Comment> comments = commentDao.getCommentList(productId);
		for (Comment c : comments) {
			char[] chars = c.getCommentScore().toString().trim().toCharArray();
			if (Character.valueOf(chars[0]) >= 4)
				result.add(c);
		}
		return result;
	}

	@Override
	public List<Comment> getLowComments(Long productId) {
		List<Comment> result = new ArrayList<>();
		List<Comment> comments = commentDao.getCommentList(productId);
		for (Comment c : comments) {
			char[] chars = c.getCommentScore().toString().trim().toCharArray();
			if (Character.valueOf(chars[0]) <= 2)
				result.add(c);
		}
		return result;
	}
}
