package com.test;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.jt.common.vo.PageObject;
import com.jt.prod.dao.ProdMenuDao;
import com.jt.prod.entity.ProdMenu;
import com.jt.sys.dao.SysConfigDao;
import com.jt.sys.entity.SysConfig;
import com.jt.sys.entity.SysRole;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysRoleService;
import com.jt.sys.service.SysUserService;
import com.jt.sys.vo.SysUserDeptResult;

public class TestProdMenuDao extends TestBase{
	
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
		ProdMenuDao dao = ctx.getBean(ProdMenuDao.class);
		System.out.println("dao = "+dao);
		List<ProdMenu> list = dao.findPageObjects("p"/*, 0, 4*/);//param1,param2,param3								
		System.out.println("dao.findPageObjects");
		for(ProdMenu sc : list){
			System.out.println(sc);
		}
	}
	
	@Test
	public void testGetRowCount(){
		ProdMenuDao dao = ctx.getBean(ProdMenuDao.class);
		int rowCount = dao.getRowCount("p");
		System.out.println("count = "+rowCount);
	}
	
	@Test
	public void testDeleteObjects(){
		ProdMenuDao dao = ctx.getBean(ProdMenuDao.class);
		int rowCount = dao.deleteObjects(2,4);
		System.out.println("删除的行数 = "+rowCount);
	}
	
	@Test
	public void testInsertObject(){
		ProdMenuDao dao = ctx.getBean(ProdMenuDao.class);
		ProdMenu entity = new ProdMenu();
		entity.setName("小红帽");
		entity.setPrice(11.9);
		int rowCount = dao.insertObject(entity);
		System.out.println("添加的行数 = "+rowCount);
	}
	
}
