package com.jd.comment.model;

import java.util.Date;

public class TradeDetail {
	private Long tradeId;
	public Long getTradeId() {
		return tradeId;
	}
	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getTradeNum() {
		return tradeNum;
	}
	public void setTradeNum(Integer tradeNum) {
		this.tradeNum = tradeNum;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public Integer getTradeIsComment() {
		return tradeIsComment;
	}
	public void setTradeIsComment(Integer tradeIsComment) {
		this.tradeIsComment = tradeIsComment;
	}
	private Long userId;
	private Long productId;
	private Integer tradeNum;
	private Date tradeTime;
	private Integer tradeIsComment;

}
