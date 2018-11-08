package com.jt.prod.entity;

import java.io.Serializable;

public class ProdOrderItem implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String itemId;
	private String orderId;
	private Integer num;
	private String title;
	private Integer price;
	private Integer totalFee;
	private String picPath;
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getItemId() {
		return itemId;
	}


	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
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


	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}


	public Integer getTotalFee() {
		return totalFee;
	}


	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}


	public String getPicPath() {
		return picPath;
	}


	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}


	@Override
	public String toString() {
		return "SysItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", num=" + num + ", title=" + title
				+ ", price=" + price + ", totalFee=" + totalFee + ", picPath=" + picPath + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
