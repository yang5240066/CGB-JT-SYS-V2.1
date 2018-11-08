package com.jt.sys.service.impl;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jt.common.annotation.RequestLog;
import com.jt.common.exception.ServiceException;
import com.jt.common.vo.CheckBox;
import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysRoleDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;
@Transactional
@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@RequestLog("角色管理分页查询操作")
	@Override
	public PageObject<SysRole>findPageObjects(
			String name, Integer pageCurrent) {
		//1.参数有效性验证
		if(pageCurrent==null||pageCurrent<1)
		throw new IllegalArgumentException("参数值无效");
		//2.基于条件查询总记录数
		int rowCount=sysRoleDao.getRowCount(name);
		//3.判定总记录数
		if(rowCount==0)
		throw new ServiceException("记录不存在");
		//4.查询当前页数据
		Integer pageSize=2;
		Integer startIndex=(pageCurrent-1)*pageSize;
		List<SysRole> records=
		sysRoleDao.findPageObjects(name, startIndex, pageSize);
		//5.封装结果
		PageObject<SysRole> pageObject=new PageObject<>();
		pageObject.setRowCount(rowCount);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRecords(records);
		pageObject.setPageCount((rowCount-1)/pageSize+1);
//		System.out.println("pageObject = "+pageObject);
		return pageObject;
	}
	
	@RequestLog("角色管理删除操作")
	@Override
	public int deleteObject(Integer id) {
		//1.校验
		if(id==null||id<1)
		throw new IllegalArgumentException("参数值不正确");
		//2.删除自身角色信息
		int rows=sysRoleDao.deleteObject(id);
		if(rows==0) throw new ServiceException("记录可能已经不存在");
		//删除角色_菜单中间表数据
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		//删除角色_用户中间表数据
		sysUserRoleDao.deleteObjectsByRoleId(id);
		//3.返回结果
		return rows;
	}
	
	/**
	 * 找出每个角色对应的菜单
	 */
	@Override
	public Map<String, Object> findObjectById(
			Integer id) {
		//1.校验
		if(id==null||id<1)
		throw new IllegalArgumentException("id的值无效");
		//2.查询
		SysRole sysRole=sysRoleDao.findObjectById(id);
		if(sysRole==null)
	    throw new ServiceException("此记录已经不存在");
		List<Integer> menuIds=
		sysRoleMenuDao.findMenuIdsByRoleId(id);
		//3.封装
		Map<String,Object> map=new HashMap<>();
		map.put("role", sysRole);
		map.put("menuIds", menuIds);
		//4.返回
		return map;
	}
	
	/**
	 * 保存角色以及角色和菜单关系数据
	 */
	@Transactional//事务控制
	@Override
	@RequestLog("角色管理保存操作")
	public int saveObject(SysRole entity, 
			Integer[] menuIds) {
		//1.校验
		if(entity==null)
		throw new IllegalArgumentException("角色信息不能为空");
		if(StringUtils.isEmpty(entity.getName()))
		throw new IllegalArgumentException("角色不能为空");
		if(menuIds==null||menuIds.length==0)
	    throw new IllegalArgumentException("必须为角色分配一个资源");
		//2.保存
		//保存角色自身信息
		int rows=sysRoleDao.insertObject(entity);
		//保存角色对应的菜单信息到中间表中
		sysRoleMenuDao.insertObject(entity.getId(),menuIds);
		return rows;
	}
	
	@RequestLog("角色管理修改操作")
	@Override
	public int updateObject(SysRole entity, 
			Integer[] menuIds) {
		//1.校验
		if(entity==null)//Spring MVC的国际化,两份配置文件,中文/英文环境
			throw new IllegalArgumentException("角色信息不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("角色名称不能为空");
		if(menuIds==null||menuIds.length==0)
			throw new IllegalArgumentException("必须为角色分配一个资源");
		//2.保存
		int rows=sysRoleDao.updateObject(entity);
		if(rows==0) throw new ServiceException("记录可能已经不存在了");
		//全部删除再增加,删除角色菜单关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
		//4.重新插入新的关系数据
		sysRoleMenuDao.insertObject(entity.getId(),menuIds);
		return rows;
	}
	
	/**
	 * ??哪里用的
	 * 获取所有的角色信息
	 */
	 @Override
	  public List<CheckBox> findObjects() {
	    	return sysRoleDao.findObjects();
	  }
	
}



