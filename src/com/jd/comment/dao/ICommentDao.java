package com.jd.comment.dao;

import java.util.List;

import com.jd.comment.entity.Comment;
import com.jd.comment.entity.User;

/**
 * Created by Ye on 2016/7/27.
 */
public interface ICommentDao {
	List<Comment> getCommentList(Long productId);

	String insertComment(User user, Comment comment);

	String appendComment(User user, Comment comment);

	int queryCount(Long productId);
}
