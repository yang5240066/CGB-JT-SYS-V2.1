package com.jt.common.vo;

import java.io.Serializable;
/**
 * zTree中的节点对象,可以借助此对象封装从
 * 数据库中查询到的菜单节点信息
 * @author UID
 *	
 */
public class Node implements Serializable{
	private static final long serialVersionUID = 4351174414771192644L;
	private Integer id;
	private String name;
	private Integer parentId;
	
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
