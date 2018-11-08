package com.jt.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jt.common.annotation.RequestLog;
import com.jt.common.exception.ServiceException;
import com.jt.common.vo.Node;
import com.jt.sys.dao.SysMenuDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.entity.SysMenu;
import com.jt.sys.service.SysMenuService;

@Transactional
@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;

	@RequestLog("菜单管理展示操作")
	@Override
	public List<Map<String, Object>> findObjects() {
		return sysMenuDao.findObjects();
	}

	@RequestLog("菜单管理删除操作")
	// 一个service对应多个dao,没有加事务控制,加了
	@Override
	public int deleteObject(Integer id) {
		// 1.参数有效性验证
		if (id == null || id < 1)
			throw new IllegalArgumentException("id 的值不正确");
		// 2.判定是否有子元素
		int count = sysMenuDao.getChildCount(id);
		if (count > 0)
			throw new ServiceException("请先删除子元素");
		// 3.删除菜单元素,只允许删除,没有子元素的菜单
		int rows = sysMenuDao.deleteObject(id);
		if (rows == 0)
			throw new ServiceException("记录可能已经不存在");
		// 4.删除角色菜单中间表的关系数据
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		// 5.返回删除的行数
		return rows;
	}
	
	@RequestLog("菜单管理保存操作")
	@Override
	public int saveObject(SysMenu entity) {
		// 1.参数有效性验证
		if (entity == null)
			throw new IllegalArgumentException("保存对象不能为空");
		if (StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("名字不能空");
		// 2.保存菜单对象到数据库,保存就不需要涉及角色表
		int rows = sysMenuDao.insertObject(entity);
		// 3.验证结果
		if (rows == 0)
			throw new ServiceException("save error");
		return rows;
	}

	@RequestLog("菜单管理修改操作")
	@Override
	public int updateObject(SysMenu entity) {
		if (entity == null)
			throw new IllegalArgumentException("参数不能为空");
		if (StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("名字不能空");
		// 更新也不需要涉及角色表
		int rows = sysMenuDao.updateObject(entity);
		return rows;
	}

	@Override
	public List<Node> findZtreeMenuNodes() {
		return sysMenuDao.findZtreeMenuNodes();
	}

}
