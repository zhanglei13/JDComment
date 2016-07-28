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

import com.jd.comment.model.ANotDetail;
import com.jd.comment.model.TradeDetail;
import com.jd.comment.service.ITradeService;

@Controller
@RequestMapping("/trade")
public class TradeController {
	protected static Logger logger = Logger.getLogger(TradeController.class);

	@Autowired
	private ITradeService tradeService;

	@RequestMapping(value = "/list")
	@ResponseBody
	public List<TradeDetail> query(Long userId) {
		return tradeService.query(userId);
	}
	
	@RequestMapping(value = "/alcom")
	@ResponseBody
	public List<ANotDetail> listAleadyComment(String username) {
		return tradeService.listAleadyComment(username);
	}
	
	@RequestMapping(value = "/nlcom")
	@ResponseBody
	public List<ANotDetail> listNotAleadyComment(String username) {
		List<ANotDetail> result = tradeService.listNotAleadyComment(username);
		return result;
	}

}
