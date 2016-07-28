package com.jd.comment.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jd.comment.entity.Product;
import com.jd.comment.entity.User;
import com.jd.comment.model.ProductDetail;
import com.jd.comment.service.IProductService;
import com.jd.comment.service.IUserService;

@Controller
@RequestMapping("/product")
public class ProductController {
	protected static Logger logger = Logger.getLogger(ProductController.class);

	@Autowired
	private IProductService productService;
	
	@Autowired
	private IUserService userService;

	// 商品详情页
	@RequestMapping(value = "/list")
	@ResponseBody
	public List<ProductDetail> inqure() {
		return productService.inqure();
	}

	// 根据商品ID查询商品详情页
	@RequestMapping(value = "/detail", method=RequestMethod.GET)
	public ModelAndView inqureProject(String productId) {
		Long id = Long.parseLong(productId);
		Product p = productService.inqure(id);
		ModelAndView modelAndView = new ModelAndView("details-data"); 
		modelAndView.addObject("product", p); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/allComment", method=RequestMethod.GET)
	public ModelAndView allComment(String productId) {
		Long id = Long.parseLong(productId);
		Product p = productService.inqure(id);
		ModelAndView modelAndView = new ModelAndView("details-all"); 
		modelAndView.addObject("product", p); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/goodComment", method=RequestMethod.GET)
	public ModelAndView goodComment(String productId) {
		Long id = Long.parseLong(productId);
		Product p = productService.inqure(id);
		ModelAndView modelAndView = new ModelAndView("details-good"); 
		modelAndView.addObject("product", p); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/picComment", method=RequestMethod.GET)
	public ModelAndView picComment(String productId) {
		Long id = Long.parseLong(productId);
		Product p = productService.inqure(id);
		ModelAndView modelAndView = new ModelAndView("details-pho"); 
		modelAndView.addObject("product", p); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/badComment", method=RequestMethod.GET)
	public ModelAndView badComment(String productId) {
		Long id = Long.parseLong(productId);
		Product p = productService.inqure(id);
		ModelAndView modelAndView = new ModelAndView("details-low"); 
		modelAndView.addObject("product", p); 
		return modelAndView;
	}

	@RequestMapping(value = "/uncomment", method = RequestMethod.POST)
	public String uncomment(HttpServletRequest request, HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
		User user = userService.getUser(username);
		
		if (user != null) {
			return "redirect:/";
		}
		return "index";
	}
	
	
	
}
