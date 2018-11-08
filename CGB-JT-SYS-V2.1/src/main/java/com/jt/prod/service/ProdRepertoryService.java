package com.jt.prod.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import com.jt.common.vo.PageObject;
import com.jt.prod.entity.ProdRepertory;

public interface ProdRepertoryService {
	
	ProdRepertory findObjectById(@Param("id")Integer id);
	
	PageObject<ProdRepertory> findPageObjects(
			@Param("name")String name,
			@Param("pageCurrent")Integer pageCurrent);
	int deleteObjects(@PathVariable Integer... id);
	
	int updateObjectById(@Param("id")Integer id);
	
	/**
	 * 更新配置信息
	 * @param entity 封装配置信息
	 * @return 表示更新的行数
	 */
	int updateObject(ProdRepertory entity);
	int saveObject(ProdRepertory entity);
	
	String updateStatusById(Integer id,Integer newStatus);
}
