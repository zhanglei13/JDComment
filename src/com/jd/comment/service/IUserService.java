package com.jd.comment.service;

import com.jd.comment.entity.User;

public interface IUserService {
	User login(String username, String password);
	
	User getUser(String username);
}
