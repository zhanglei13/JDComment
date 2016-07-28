package com.jd.comment.dao.impl;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.Date;

import org.apache.log4j.Logger;

import com.jd.comment.dao.IUserDao;
import com.jd.comment.entity.Comment;
import com.jd.comment.entity.User;

@Repository("userDao")
public class UserDao extends HibernateBaseDao<User, Long>implements IUserDao {

	protected static final Logger LOGGER = Logger.getLogger(UserDao.class);

	@Override
	public User login(String username, String password) {
		Criterion c1 = Restrictions.eq("userName", username);
		Criterion c2 = Restrictions.eq("password", password);
		return this.findUnique(User.class, c1, c2);
	}

	public User getUser(String username) {
		return this.findUnique(User.class, Restrictions.eq("userName", username));
	}

//	public static void main(String[] args) {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-*.xml");
//		IUserDao dao = ctx.getBean(UserDao.class);
//		System.out.println(dao.login("zhanglei", "123"));
//	}

}
