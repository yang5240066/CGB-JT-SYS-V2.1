package com.jt.prod.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.prod.entity.ProdCart;
import com.jt.prod.entity.ProdOrder;
import com.jt.sys.entity.SysConfig;

public interface ProdCartDao {
	
	List<ProdCart> doFindObjects(Integer id);
	
	int deleteObjects(@Param("ids")Integer... ids);
	
	
	  /**
     * 基于条件查询当前页数据
     * @param name 查询时输出的参数名
     * @param startIndex 当前起始位置
     * @param pageSize 每页最多显示的记录数,页面大小.
     * @return
     */
	List<ProdCart> findPageObjects(
			@Param("userId")Integer userId,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	/**
	 * 基于条件查询总记录数
	 * @param name 查询条件
	 * @return
	 */
	int getRowCount(@Param("userId")Integer userId);
	
	
	int insertObject(ProdOrder entity);
	
	
	
	ProdCart getObjectId(@Param("id")Integer id);
	
	int insertWuPin(ProdCart entity);
	
	int findByUserId(ProdCart entity);
	
	int updateDeNumber(ProdCart entity);
	
	int updateNumber(ProdCart entity);
	
	int deletByUserId(ProdCart entity);
	
	int deleteWuPin(ProdCart entity);
}
