package com.jd.comment.dao.impl;

import org.springframework.stereotype.Repository;

import com.jd.comment.dao.IAdminDao;

@Repository("adminDao")
public class AdminDao implements IAdminDao {
	@Override
	public boolean adminLogin(String name, String password) {
		return "admin".equals(name.trim()) && "admin".equals(password.trim());
	}
}
