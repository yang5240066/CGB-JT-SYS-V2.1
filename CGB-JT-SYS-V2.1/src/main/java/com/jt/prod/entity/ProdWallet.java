package com.jt.prod.entity;

public class ProdWallet {
	private Integer id;
	private Integer userId;
	private Double balance;
	private String payPassword;
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
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	@Override
	public String toString() {
		return "ProdWallet [id=" + id + ", userId=" + userId + ", balance=" + balance + ", payPassword=" + payPassword
				+ "]";
	}
	
}
