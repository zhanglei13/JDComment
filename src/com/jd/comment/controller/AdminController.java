package com.jd.comment.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jd.comment.model.ProductDetail;
import com.jd.comment.service.IAdminService;
import com.jd.comment.util.ReadData;

@Controller
@RequestMapping("/admin")
public class AdminController {
	protected static Logger logger = Logger.getLogger(AdminController.class);
	
	@Autowired
	private IAdminService adminService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpSession session, Model model) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String info = "Login failed!";
		String failure = "redirect:/";
		if (username.isEmpty())
			info = "Please input your username!";
		else if (password.isEmpty())
			info = "Please input your password!";
		else {
			if (adminService.login(username, password)) {
				info = "login success";
				return "forward:/index";
			}
		}

		model.addAttribute("info", info);
		return failure;
	}
	
	@RequestMapping(value = "/file1")
	@ResponseBody
	public Map<String,String> file1() {
		String filePath1 = "C:\\Users\\zhanglei\\Desktop\\MiningResult1.txt";
		Map<String,String> m1 = ReadData.readTxtFile(filePath1);
		return m1;
	}
	
	@RequestMapping(value = "/file2")
	@ResponseBody
	public Map<String,String> file2() {
		String filePath1 = "C:\\Users\\zhanglei\\Desktop\\MiningResult2.txt";
		Map<String,String> m1 = ReadData.readTxtFile(filePath1);
		return m1;
	}
	
	@RequestMapping(value = "/file3")
	@ResponseBody
	public Map<String,String> file3() {
		String filePath1 = "C:\\Users\\zhanglei\\Desktop\\MiningResult3.txt";
		Map<String,String> m1 = ReadData.readTxtFile(filePath1);
		return m1;
	}
}
