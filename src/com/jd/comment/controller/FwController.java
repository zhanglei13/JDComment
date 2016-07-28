package com.jd.comment.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jd.comment.entity.User;
import com.jd.comment.service.IUserService;
import com.jd.comment.util.Constants;

@Controller
@RequestMapping
public class FwController {
	protected static Logger logger = Logger.getLogger(FwController.class);
	
	@Autowired
	private IUserService userService;
	
	 /**
     * 
     * 名称：to500 <br/>
     * 描述：500错误跳转 <br/>
     * 
     * @return String
     */
    @RequestMapping(value = "/to500", produces = "text/html;charset=UTF-8")
    public String to500() {
        return "/error/500";
    }

    /**
     * 
     * 名称：to404 <br/>
     * 描述：404错误跳转 <br/>
     * 
     * @return String
     */
    @RequestMapping(value = "/to404", produces = "text/html;charset=UTF-8")
    public String to404() {
        return "/error/404";
    }

    /**
     * 
     * 名称：index <br/>
     * 描述：登入成功后跳到首页 <br/>
     * 
     * @return String
     */
    @RequestMapping(value = "/login", produces = "text/html;charset=UTF-8")
    public String login() {
        return "login";
    }
    
    @RequestMapping(value = "/ulogin", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String ulogin(HttpServletRequest request, HttpSession session) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userService.login(username, password);
		if (user != null) {
			session.setAttribute("username", username);
			return "index";
		}
		return "login";
	}
    
    
    @RequestMapping(value = "/index", produces = "text/html;charset=UTF-8")
    public String index() {
        return "index";
    }
    
    @RequestMapping(value = "/form", produces = "text/html;charset=UTF-8")
    public String form() {
        return "index-form";
    }
    
    @RequestMapping(value = "/oldindex", produces = "text/html;charset=UTF-8")
    public String oldIndex() {
        return "old-index";
    }
    
    /**
     * 
     * 名称：logOut <br/>
     * 描述：登入注销 <br/>
     * 
     * @return String
     */
    @RequestMapping(value = "/logout", produces = "text/html;charset=UTF-8")
    public String logOut(HttpSession session, Model model) {
        if (session.getAttribute("username") != null) {
            session.removeAttribute("username");
        }
        return "redirect:/";
    }
    
    @RequestMapping(value = "/manage", produces = "text/html;charset=UTF-8")
    public String manage() {
        return "admin";
    }
}
