package com.jd.comment.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "user", catalog = "comment")
public class User {

	// Fields

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	private Long userId;

	@Column(name = "user_name", length = 50)
	private String userName;

	@Column(name = "user_password", length = 50)
	private String password;

    @Column(name = "user_age", length = 2)
    private Integer userAge;

    @Column(name = "user_ip", length = 20)
    private String user_ip;

	@Column(name = "user_creditLevel")
	private Integer userCreditLevel;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserPassword(String password) {
        this.password = password;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public Integer getUserCreditLevel() {
        return userCreditLevel;
    }

    public void setUserCreditLevel(Integer userCreditLevel) {
        this.userCreditLevel = userCreditLevel;
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