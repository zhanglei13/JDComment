package com.jd.comment.dao;

import com.jd.comment.entity.User;

public interface IUserDao {
	User login(String username, String password);
	
	User getUser(String username);
	
}
