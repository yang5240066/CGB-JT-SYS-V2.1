package com.jt.prod.entity;

import java.util.Date;

public class ProdRecharge {
	private Integer id;
	private Integer userId;
	private Date rechargeTime;
	private Double rechargeAmount;
	private Double beforeBalance;
	private Double afterBalance;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getRechargeTime() {
		return rechargeTime;
	}
	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}
	public Double getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(Double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public Double getBeforeBalance() {
		return beforeBalance;
	}
	public void setBeforeBalance(Double beforeBalance) {
		this.beforeBalance = beforeBalance;
	}
	public Double getAfterBalance() {
		return afterBalance;
	}
	public void setAfterBalance(Double afterBalance) {
		this.afterBalance = afterBalance;
	}
	@Override
	public String toString() {
		return "ProdRecharge [id=" + id + ", userId=" + userId + ", rechargeTime=" + rechargeTime + ", rechargeAmount="
				+ rechargeAmount + ", beforeBalance=" + beforeBalance + ", afterBalance=" + afterBalance + "]";
	}
	
}
