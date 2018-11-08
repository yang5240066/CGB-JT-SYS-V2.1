package com.jt.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jt.common.vo.Node;
import com.jt.sys.entity.SysMenu;


public interface SysMenuDao {

	//这是在别的地方用的
	List<String> findPermissions(
			@Param("menuIds")
			Integer... menuIds);


	int updateObject(SysMenu entity);

	/**
	 * 将数据持久化到数据库
	 * @param entity
	 * @return int 写入的行数
	 */
	int insertObject(SysMenu entity);

	/**
	 * 查询菜单节点,此信息会在客户端
	 * 的zTree对象上进行呈现.
	 * @return
	 */
	List<Node> findZtreeMenuNodes();

	/**
	 * 基于菜单id统计子菜单的个数
	 * @param id
	 * @return
	 */
	int getChildCount(Integer id);

	/**
	 * 基于菜单id执行删除操作
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);

	/**
	 * 查询所有菜单以及上一级菜单
	 * 假如本菜单没有上一级菜单是否要呈现
	 * 当菜单为一级菜单时,它的上一级菜单默认为null?
	 * 这样的菜单,采用怎样的sql实现查询操作,关联关系
	 * 方案:表关联或嵌套查询
	 * @param menuIds
	 * @return
	 */
	/**
	 * 查询所有菜单以及上级菜单信息(菜单名) 
	 * @return
	 */
	List<Map<String,Object>> findObjects();
}








