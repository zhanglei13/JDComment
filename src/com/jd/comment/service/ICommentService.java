package com.jd.comment.service;

import java.util.List;

import com.jd.comment.entity.Comment;
import com.jd.comment.entity.User;

public interface ICommentService {
	
	List<Comment> getCommentList(Long productId);
	
	String insertComment(User user, Comment comment);
	
	String appendComment(User user, Comment comment);

	List<Comment> getAllComments(Long productId);
	
	List<Comment> getGoodComments(Long productId);
	
	List<Comment> getLowComments(Long productId);
}
