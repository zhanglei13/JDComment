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
@Table(name = "Comment", catalog = "comment")
public class Comment {
	
    // Fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", unique = true, nullable = false)
    private Long commentId;

    @Column(name = "comment_parentid", length = 50)
    private Long commentParentId;

    @Column(name = "product_id", length = 50)
    private Long productId;

    @Column(name = "user_id", length = 50)
    private Long userId;

    @Column(name = "comment_text", length = 300)
    private String commnetText;

    @Column(name = "comment_score", length = 3)
    private Integer commentScore;

    @Column(name = "comment_isaudit")
    private Integer commentIsAudit;

    @Column(name = "comment_state")
    private Integer commentState;

    @Column(name = "comment_auditor", length = 20)
    private String commentAuditor;

    @Column(name = "comment_time")
    private Date commentTime;

    @Column(name = "comment_anonymous")
    private Integer commentAnonymous;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getCommentParentId() {
        return commentParentId;
    }

    public void setCommentParentId(Long commentParentId) {
        this.commentParentId = commentParentId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCommnetText() {
        return commnetText;
    }

    public void setCommnetText(String commnetText) {
        this.commnetText = commnetText;
    }

    public Integer getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(Integer commentScore) {
        this.commentScore = commentScore;
    }

    public Integer getCommentIsAudit() {
        return commentIsAudit;
    }

    public void setCommentIsAudit(Integer commentIsAudit) {
        this.commentIsAudit = commentIsAudit;
    }

    public Integer getCommentState() {
        return commentState;
    }

    public void setCommentState(Integer commentState) {
        this.commentState = commentState;
    }

    public String getCommentAuditor() {
        return commentAuditor;
    }

    public void setCommentAuditor(String commentAuditor) {
        this.commentAuditor = commentAuditor;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getCommentAnonymous() {
        return commentAnonymous;
    }

    public void setCommentAnonymous(Integer commentAnonymous) {
        this.commentAnonymous = commentAnonymous;
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
