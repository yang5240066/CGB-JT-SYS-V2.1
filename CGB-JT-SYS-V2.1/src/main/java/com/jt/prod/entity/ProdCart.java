package com.jt.prod.entity;

import java.io.Serializable;

public class ProdCart implements Serializable{

	private static final long serialVersionUID = -4911804768354772951L;
	private Integer id;
	private Integer userId;
	private String name;
	private String type;
	private Integer number;
	private Double price;
	private String receiverName;
	private String address;
	private String receiverMobile;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getReceiverMobile() {
		return receiverMobile;
	}
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ProdCart [id=" + id + ", userId=" + userId + ", name=" + name + ", type=" + type + ", number=" + number
				+ ", price=" + price + ", receiverName=" + receiverName + ", address=" + address + ", receiverMobile="
				+ receiverMobile + "]";
	}
	
}
