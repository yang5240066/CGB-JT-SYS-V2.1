package com.test;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysConfigDao;
import com.jt.sys.entity.SysConfig;
import com.jt.sys.entity.SysRole;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysRoleService;
import com.jt.sys.service.SysUserService;
import com.jt.sys.vo.SysUserDeptResult;

public class TestSysConfigDao extends TestBase{
	
	@Test
	public void test01(){
		SysUserService userService = ctx.getBean(SysUserService.class);
		PageObject<SysUserDeptResult> po = userService.findPageObjects("", 1);
		System.out.println("po2 = "+po);
		List<SysUserDeptResult> records = po.getRecords();
		System.out.println(records.size());
		for(SysUserDeptResult o: records){
			System.out.println(o);
		}
	}
	
	
	@Test
	public void testDataSource(){
		DataSource ds = ctx.getBean(DataSource.class);
		System.out.println(ds);
	}
	
	@Test
	public void testSqlSessionFactory(){
		SqlSessionFactory ssf = ctx.getBean(SqlSessionFactory.class);
		System.out.println("ssf = "+ssf);
	}
	
	@Test
	public void testFindPageObjects(){
		SysConfigDao dao = ctx.getBean(SysConfigDao.class);
		System.out.println("dao = "+dao);
		List<SysConfig> list = dao.findPageObjects("p", 0, 2);//param1,param2,param3								
		System.out.println("dao.findPageObjects");
		for(SysConfig sc : list){
			System.out.println(sc);
		}
	}
	
	@Test
	public void testGetRowCount(){
		SysConfigDao dao = ctx.getBean(SysConfigDao.class);
		int rowCount = dao.getRowCount("d");
		System.out.println("count = "+rowCount);
	}
	
	@Test
	public void testDeleteObjects(){
		SysConfigDao dao = ctx.getBean(SysConfigDao.class);
		int rowCount = dao.deleteObjects(10,11);
		System.out.println("删除的行数 = "+rowCount);
	}
	
	@Test
	public void testInsertObject(){
		SysConfigDao dao = ctx.getBean(SysConfigDao.class);
		SysConfig entity = new SysConfig();
		entity.setName("Kevinbbb");
		entity.setValue("aaa");
		int rowCount = dao.insertObject(entity);
		System.out.println("添加的行数 = "+rowCount);
	}
	
}
