package com.jd.comment.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jd.comment.entity.User;
import com.jd.comment.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	protected static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private IUserService userService;

	@RequestMapping("/get")
	@ResponseBody
	public User getUserByName(String username) {
		return userService.getUser(username);
	}
}
