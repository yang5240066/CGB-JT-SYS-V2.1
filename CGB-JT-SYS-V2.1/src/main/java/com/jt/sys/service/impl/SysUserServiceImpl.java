package com.jt.sys.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jt.common.vo.PageObject;
import com.jt.common.annotation.RequestLog;
import com.jt.common.exception.ServiceException;
import com.jt.sys.dao.SysMenuDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
import com.jt.sys.vo.SysUserDeptResult;

@Service
@Transactional(rollbackFor=Throwable.class)//只要有异常或者错误,就回滚
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysMenuDao sysMenuDao;
    @Autowired
	private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    
    @RequestLog("用户管理分页查询操作")
	@Transactional(
	isolation=Isolation.READ_COMMITTED,//隔离级别,读取的都是别人提交的数据,不允许脏读
	timeout=30)
    @Override
    //返回的SysUserDeptResult包含User和它对应dept信息
	public PageObject<SysUserDeptResult> 
	findPageObjects(String username, Integer pageCurrent) {
    	System.out.println(55);
		//1.合法验证
	    if(pageCurrent==null||pageCurrent<1)
	    throw new IllegalArgumentException("当前页码值无效");
		//2.查询总记录数
	    int rowCount=sysUserDao.getRowCount(username);
		//3.对记录数进行验证
	    if(rowCount==0)
	    throw new ServiceException("没有记录");//中断方法执行
		//4.查询当前页数据
	    int pageSize=5;
	    int startIndex=(pageCurrent-1)*pageSize;
	    List<SysUserDeptResult> records=
	    sysUserDao.findPageObjects(username,startIndex, pageSize);
		//5.对数据进行封装
	    PageObject<SysUserDeptResult> po=new PageObject<>();
	    po.setRowCount(rowCount);
	    po.setRecords(records);
	    po.setPageSize(pageSize);
	    po.setPageCurrent(pageCurrent);
	    po.setPageCount((rowCount-1)/pageSize+1);//总页数
		//6.返回结果
	    //System.out.println("po = "+po);
		return po;
	}
    
    /**
     * 访问业务方法需要权限检测时
     * 需要添加Shiro框架中的
     * @RequiresPermissions注解
     * 当底层系统运行时检测到方法上
     * 使用了@RequiresPermissions注解
     * 就会为业务对象创建一个代理对象,
     * 然后在代理对象中调用
     * subject.isPermitted("sys:user:valid")
     * 方法进行权限检测操作
     */
    @RequestLog("用户管理启用/禁用操作")
	@Transactional(
	isolation=Isolation.READ_COMMITTED,//隔离级别,读取的都是别人提交的数据,不允许脏读
	timeout=30)
    @RequiresPermissions("sys:user:valid")
    @Override
    public int validById(Integer id,
    		Integer valid,
    		String modifiedUser) {
    	//1.参数有效性验证
    	if(id==null||id<1)
    	throw new IllegalArgumentException("id值无效");//参数异常
    	if(valid==null||(valid!=1&&valid!=0))//&优先级高于|
    	throw new IllegalArgumentException("状态值无效");
    	int rows=sysUserDao.validById(id,valid,modifiedUser);
    	if(rows==0)
    	throw new ServiceException("记录可能已经不存在");//抛出自定义业务异常
    	return rows;
    }
    
    @RequestLog("用户管理保存操作")
	@Transactional(
	isolation=Isolation.READ_COMMITTED,//隔离级别,读取的都是别人提交的数据,不允许脏读
	timeout=30)
    @Override
    public int saveObject(SysUser entity,Integer[] roleIds) {
    	//1.参数合法验证
    	if(entity==null)
    	throw new IllegalArgumentException("保存对应不能为空");
    	//...验证名字是否为空,是否唯一,...
    	//...验证密码是否为空,是不是数字和字母的组合,长度,,
    	if(roleIds==null||roleIds.length==0)
    	throw new IllegalArgumentException("必须为用户分配角色");
    	//2.保存数据
    	//2.1 创建一个盐值(用于辅助加密,保证密码更加安全的一种手段)
    	String salt=UUID.randomUUID().toString();//随机的字符串
    	String pwd=entity.getPassword();
    	//2.3 对密码进行加密,加密算法md5
    	SimpleHash sh=//这个api属于shiro框架,后续需要引入shiro依赖
    	new SimpleHash("MD5",//algorithmName 表示加密算法,hash算法
    			pwd, //source 为要加密的对象
    			salt,//salt 加密盐值
    			1);//加密的次数
    	entity.setPassword(sh.toHex());//把加密后的密码加进去,toHex就是toString
    	entity.setSalt(salt);
    	//2.4设置对象其它属性默认值
    	entity.setCreatedTime(new Date());
    	entity.setModifiedTime(new Date());
    	//2.5保存用户自身信息
    	int rows=sysUserDao.insertObject(entity);
    	//2.6保存用户与角色的关系数据
    	sysUserRoleDao.insertObject(entity.getId(),roleIds);
    	//3.返回结果
    	return rows;
    }
    
    @RequestLog("用户管理修改操作")
	@Transactional(
	isolation=Isolation.READ_COMMITTED,//隔离级别,读取的都是别人提交的数据,不允许脏读
	timeout=30)
    @Override
    public int updateObject(SysUser entity, Integer[] roleIds) {
    	//1.校验
    	if(entity==null)
    	throw new IllegalArgumentException("更新对象不能为空");
    	if(StringUtils.isEmpty(entity.getUsername()))
    	throw new IllegalArgumentException("用户名不能为空");
    	if(roleIds==null||roleIds.length==0)
    	throw new IllegalArgumentException("需要为用户分配角色");
//    	如果密码为空,就不修改密码
    	if(!StringUtils.isEmpty(entity.getPassword())){
    		String salt=UUID.randomUUID().toString();
    		entity.setSalt(salt);//产生一个新的盐值
    		SimpleHash sh=
    		new SimpleHash("MD5",entity.getPassword(),salt);
    		entity.setPassword(sh.toString());
    	}
    	//2.更新
    	//更新用户自身的信息
    	int rows=sysUserDao.updateObject(entity);
    	//基于用户id删除用户对应的角色id
    	sysUserRoleDao.deleteObjectsByUserId(entity.getId());
    	//增加用户新的角色信息,其实这两步执行的就是用户角色中间表的更新操作
    	sysUserRoleDao.insertObject(entity.getId(), roleIds);
    	//3.返回
    	return rows;
    }
    
    @Override
    public Map<String, Object> 
        findObjectById(Integer id) {
    	//1.参数有效性验证
    	if(id==null||id<1)
    	throw new IllegalArgumentException("id的值无效");
    	//2.查询用户以及部门信息
    	SysUserDeptResult user= sysUserDao.findObjectById(id);
    	if(user==null)
    	throw new ServiceException("此用户可能已经不存在");
    	//3.查询用户对应的角色信息
    	List<Integer> roleIds=
    	sysUserRoleDao.findRoleIdsByUserId(id);
    	//4.封装数据并返回.
    	Map<String,Object> map=new HashMap<>();
    	map.put("user", user);
    	map.put("roleIds", roleIds);
    	
    	return map;
    }

	@Override
	public Integer[] findMenuIdsByUserId(Integer userId) {
		List<Integer> roleIds=sysUserRoleDao.findRoleIdsByUserId(userId);
		//System.out.println("roleIds="+roleIds);
		//2.基于角色id查找菜单id
		Integer[] array={};
		List<Integer>  menuIds=sysRoleMenuDao.findTypeOneMenuIdsByRoleId(
//		把roleIds list集合转换成数组
		roleIds.toArray(array));
		Integer[] arrMenuIds={};
		return menuIds.toArray(arrMenuIds);
	}
    
	
}





