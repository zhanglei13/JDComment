package com.jd.comment.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.comment.dao.IUserDao;
import com.jd.comment.entity.User;
import com.jd.comment.service.IUserService;

@Service("userService")
public class UserService implements IUserService {

	protected static Logger logger = Logger.getLogger(UserService.class);
	
	@Autowired
    protected IUserDao userDao;
	
	@Override
	public User login(String username, String password) {
		return userDao.login(username, password);
	}
	
	@Override
	public User getUser(String username) {
		return userDao.getUser(username);
	}
	
}
