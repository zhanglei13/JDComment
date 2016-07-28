package com.jd.comment.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.comment.dao.IAdminDao;
import com.jd.comment.service.IAdminService;

@Service("adminService")
public class AdminService implements IAdminService {
	protected static Logger logger = Logger.getLogger(AdminService.class);

	@Autowired
	protected IAdminDao adminDao;

	@Override
	public boolean login(String username, String password) {
		return adminDao.adminLogin(username, password);
	}
}
