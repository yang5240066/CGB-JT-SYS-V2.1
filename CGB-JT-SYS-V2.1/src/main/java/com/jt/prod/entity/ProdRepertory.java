package com.jt.prod.entity;

import java.io.Serializable;

public class ProdRepertory implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String category;
	private Double price;
	private Double pnum;
	private String description;
	private Integer onSale;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPnum() {
		return pnum;
	}
	public void setPnum(Double pnum) {
		this.pnum = pnum;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getOnSale() {
		return onSale;
	}
	public void setOnSale(Integer onSale) {
		this.onSale = onSale;
	}
	@Override
	public String toString() {
		return "ProdRepertory [id=" + id + ", name=" + name + ", category=" + category + ", price=" + price + ", pnum="
				+ pnum + ", description=" + description + ", onSale=" + onSale + "]";
	}
	
	
}
