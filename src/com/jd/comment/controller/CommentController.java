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

import com.jd.comment.entity.Comment;
import com.jd.comment.entity.User;
import com.jd.comment.service.ICommentService;
import com.jd.comment.service.ITradeService;
import com.jd.comment.service.IUserService;

@Controller
@RequestMapping("/comment")
public class CommentController  {
	protected static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private ICommentService commentService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ITradeService tradeService;

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public List<Comment> getComment(HttpServletRequest request, HttpSession session, Model model) {
		String productIdString = request.getParameter("productId");
		Long productId = Long.valueOf(productIdString);
		List<Comment> comments = commentService.getCommentList(productId);
		
		return comments;
	}
	
	@RequestMapping(value = "/commentadd", method = RequestMethod.POST)
	public String addComment(HttpServletRequest request, HttpSession session){
		Long productId = Long.valueOf(request.getParameter("productId"));
		String commentText = request.getParameter("ctext");
		String scoreString = request.getParameter("goods")+
				request.getParameter("stores")+request.getParameter("logistics");
		int commentScore = Integer.parseInt(scoreString);
		int commentAnonymous = request.getParameter("commentAnonymous")==null?0:1;
		String userName = (String)session.getAttribute("username");
		
		Comment comment = new Comment();
		comment.setCommentParentId((long) 0);
		comment.setCommnetText(commentText);
		comment.setCommentScore(commentScore);
		comment.setCommentIsAudit(0);
		comment.setCommentAnonymous(commentAnonymous);
		comment.setProductId(productId);
		
		User user = userService.getUser(userName);
		commentService.insertComment(user, comment);
		
		tradeService.updatestate(productId,user.getUserId());
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/commentappend", method = RequestMethod.POST)
	public String appendComment(HttpServletRequest request, HttpSession session){
		Long productId = Long.valueOf(request.getParameter("productId"));
		String commentText = request.getParameter("commentText");
		String scoreString = request.getParameter("score1")+
				request.getParameter("score2")+request.getParameter("score3");
		int commentScore = Integer.parseInt(scoreString);
		int commentAnonymous = Integer.parseInt(request.getParameter("commentAnonymous"));
		Long commentParentId = Long.valueOf(request.getParameter("commentParentId"));
		String userName = request.getParameter("userName");
		
		Comment comment = new Comment();
		comment.setCommentParentId(commentParentId);
		comment.setCommnetText(commentText);
		comment.setCommentScore(commentScore);
		comment.setCommentIsAudit(0);
		comment.setCommentAnonymous(commentAnonymous);
		comment.setProductId(productId);
		
		User user = userService.getUser(userName);
		commentService.appendComment(user, comment);
		
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/allComments")
	@ResponseBody
	public List<Comment> getAllComments(String productId) {
		Long id = Long.parseLong(productId);
		return commentService.getAllComments(id);
	}
	
	@RequestMapping(value = "/goodComments")
	@ResponseBody
	public List<Comment> getGoodComments(String productId) {
		Long id = Long.parseLong(productId);
		return commentService.getGoodComments(id);
	}
	
	@RequestMapping(value = "/lowComments")
	@ResponseBody
	public List<Comment> getLowComments(String productId) {
		Long id = Long.parseLong(productId);
		return commentService.getLowComments(id);
	}
	
}