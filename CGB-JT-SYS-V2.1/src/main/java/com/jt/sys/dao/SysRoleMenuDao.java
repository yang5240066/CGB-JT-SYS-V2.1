package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 市场规则:
 * 1)一个表对应一个映射文件
 * 2)一个映射文件对应一个DAO接口
 * @author UID
 *
 */
public interface SysRoleMenuDao {
	/**
	 * 基于角色查询菜单id
	 * @param roleId
	 * @return
	 */
	List<Integer> findMenuIdsByRoleId(
			@Param("roleIds")Integer... roleIds);
	
	 /**
	  * 插入角色和菜单的关系数据,角色与菜单是多对多的关系
	  * 这种关系的维护在表设计领域中,是通过中间表来实现的
	  * 保存角色和菜单的关系数据
	  * @param roleId	角色ID
	  * @param menuIds	菜单ID
	  * @return
	  */
	 int insertObject(
			 @Param("roleId") Integer roleId,
			 @Param("menuIds")Integer[] menuIds);
	
	 /**
	  * 基于菜单id删除角色和菜单关系表中的数据
	  * @param menuId
	  * @return
	  */
	 int deleteObjectsByMenuId(Integer menuId);
	 
	 /**
	  * 基于角色id删除角色菜单关系中的记录
	  * @param roleId
	  * @return
	  */
	 int deleteObjectsByRoleId(Integer roleId);
	 
	 /**
		 * 基于角色查询一级菜单id
		 * @param roleId
		 * @return
		 */
		List<Integer> findTypeOneMenuIdsByRoleId(
				@Param("roleIds")Integer... roleIds);
	 
}
