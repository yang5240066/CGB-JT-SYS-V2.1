package com.jt.prod.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.prod.entity.ProdRepertory;


public interface ProdRepertoryDao {
	/**查询当前页面*/
	List<ProdRepertory> doFindPageObject(
			@Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	/**查找总记录数*/
	int rowCount(@Param("name")String name);


	ProdRepertory findObjectById(@Param("id")Integer id);
	
	//删除方法,根据id数组来删除
	int deleteObjects(@Param("ids")Integer... ids);
	/**
	 * 添加方法,传入的参数为Pojo,将数据持久化(映射)到数据库
	 * @param entity
	 * @return
	 */
	int insertObject(ProdRepertory entity);

	/**
	 * 更新配置信息
	 * @param entity 封装配置信息
	 * @return 表示更新的行数
	 */
	int updateObject(ProdRepertory entity);
	
	int selectPnumByName(@Param("name")String name);
	
	int updatePnumByName(@Param("pnum")Integer pnum,@Param("name")String name);
	
	int updateStatusById(@Param("id")Integer id,@Param("status")Integer status);

}
