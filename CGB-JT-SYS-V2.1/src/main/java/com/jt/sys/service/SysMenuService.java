package com.jt.sys.service;

import java.util.List;
import java.util.Map;

import com.jt.common.vo.Node;
import com.jt.sys.entity.SysMenu;


public interface SysMenuService {
	
	 int updateObject(SysMenu entity);
	
	 int saveObject(SysMenu entity);
	
	 List<Node> findZtreeMenuNodes();
	 
	/**
	 * 基于菜单id执行菜单删除操作
	 * @param id
	 * @return
	 */
	 int deleteObject(Integer id);
  /**
   * 查询所有菜单以及上级菜单信息
   * @return
   */
	 List<Map<String,Object>> findObjects();
}
