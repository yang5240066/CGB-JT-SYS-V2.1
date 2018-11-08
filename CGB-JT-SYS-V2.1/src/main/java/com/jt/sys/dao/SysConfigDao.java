package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.sys.entity.SysConfig;

public interface SysConfigDao {
	/**
	 * 按条件执行分页查询
	 * @param name	查询条件
	 * @param startIndex	当前页的起始位置
	 * @param pageSize	页面大小(当前页要取的条数)
	 * @return
	 */
	List<SysConfig> findPageObjects(
			@Param("name")String name,//param1或者0,1,2
			@Param("startIndex")Integer startIndex,//param2
			@Param("pageSize")Integer pageSize);//param3
	
	/**
	 * 依据条件统计数据库中的总记录数
	 * @param name
	 * @return
	 */
	int getRowCount(@Param("name")String name);
	
	//删除方法,根据id数组来删除
	int deleteObjects(@Param("ids")Integer... ids);
	/**
	 * 添加方法,传入的参数为Pojo,将数据持久化(映射)到数据库
	 * @param entity
	 * @return
	 */
	int insertObject(SysConfig entity);
	
	/**
	 * 更新配置信息
	 * @param entity 封装配置信息
	 * @return 表示更新的行数
	 */
	int updateObject(SysConfig entity);

}











