package com.jt.common.vo;

import java.util.List;

/**
 * VO(Value Object):值对象
 * 借助此对象封装业务层查询结果
 * 1)当前页记录(List)
 * 2)分页信息
 * @author UID
 * @param <T>
 *
 */
public class PageObject<T> {
	//每页要显示的最大记录数
	private Integer pageSize = 3;
	
	//当前页
	private Integer pageCurrent;
	
	/**总页数*/
	private Integer pageCount;
	
	/**总记录数*/
	private Integer rowCount;
	
	/**当前页数据(记录)*/
	private List<T> records;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageCurrent() {
		return pageCurrent;
	}

	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	@Override
	public String toString() {
		return "PageObject [pageSize=" + pageSize + ", pageCurrent=" + pageCurrent + ", pageCount=" + pageCount
				+ ", rowCount=" + rowCount + ", records=" + records + "]";
	}

}










