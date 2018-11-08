package com.jt.prod.service;

import java.util.List;

import com.jt.common.vo.PageObject;
import com.jt.prod.entity.ProdCart;
import com.jt.prod.entity.ProdMenu;
import com.jt.prod.entity.ProdRepertory;

public interface ProdMenuService {
	/**
	 * 通过id查找商品
	 * @param id
	 * @return
	 */
	ProdMenu getObjectById(Integer id);
	/**
	 * 依据条件执行分页查询
	 * @param name	查询条件
	 * @param pageCurrent	当前页页码(要查询的那一页)
	 * @return
	 */
	PageObject<ProdMenu> findPageObjects(
			String name,
			Integer pageCurrent);
	
	/**
	 * 删除方法,根据id数组来删除,ids此值来自页面(选中的多条id值)
	 * @param ids
	 * @return
	 */
	int deleteObjects(Integer... ids);
	/**
	 * 添加方法,传入的参数为Pojo
	 * @param entity
	 * @return
	 */
	int saveObject(ProdMenu entity);
	/**
	 * 更新配置信息
	 * @param entity 封装配置信息
	 * @return 表示更新的行数
	 */
	int updateObject(ProdMenu entity);

	
	List<ProdMenu> findAllPageObjects();

	
	int insertWuPin(ProdCart entity);

}
