package com.jd.comment.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

import javax.persistence.*;

/**
 * Created by Ye on 2016/7/27.
 */
@Entity
@Table(name = "trade", catalog = "comment")
public class Trade {
    // Fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id", unique = true, nullable = false)
    private Long tradeId;

    @Column(name = "user_id", length = 50)
    private Long userId;

    @Column(name = "product_id", length = 50)
    private Long productId;

    @Column(name = "trade_num", length = 20)
    private Integer tradeNum;

    @Column(name = "trade_time")
    private Date tradeTime;

    @Column(name = "trade_iscomment")
    private Integer tradeIsComment;
    
    @Column(name = "commet_id",length = 50)
    private Integer commetId;

    public Integer getCommetId() {
		return commetId;
	}

	public void setCommetId(Integer commetId) {
		this.commetId = commetId;
	}

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

    @Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
