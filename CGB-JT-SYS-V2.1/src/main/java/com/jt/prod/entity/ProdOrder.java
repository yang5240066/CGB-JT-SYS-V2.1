package com.jt.prod.entity;

import java.io.Serializable;
import java.util.Date;

public class ProdOrder implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer orderId;
	private String status;
	private Date createTime;
	private Date paymentTime;
	private Integer userId;
	private Integer num;
	private String title;
	private Double price;
	private Double totalFee;
	private String receiverName;
	private String receiverAddress;
	private String receiverMobile;
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverMobile() {
		return receiverMobile;
	}
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	@Override
	public String toString() {
		return "ProdOrder [orderId=" + orderId + ", status=" + status + ", createTime=" + createTime + ", paymentTime="
				+ paymentTime + ", userId=" + userId + ", num=" + num + ", title=" + title
				+ ", price=" + price + ", totalFee=" + totalFee + ", receiverName=" + receiverName
				+ ", receiverAddress=" + receiverAddress + ", receiverMobile=" + receiverMobile + "]";
	}
	
	
	
}
