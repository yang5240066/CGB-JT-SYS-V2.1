package com.test;

import org.junit.Test;

import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysConfigDao;
import com.jt.sys.entity.SysConfig;
import com.jt.sys.service.SysConfigService;

public class TestSysConfigService extends TestBase{
	
	
	@Test
	public void testFindPageObjects(){
		System.out.println("service.findPageObjects");
		SysConfigService service = ctx.getBean("sysConfigServiceImpl",SysConfigService.class);
		PageObject<SysConfig> pageObject = service.findPageObjects("d", 1);
		System.out.println(pageObject);
	}
	
	@Test
	public void testDeleteObjects(){
		System.out.println("TestSysConfigService.testDeleteObjects()");
		SysConfigService service = ctx.getBean("sysConfigServiceImpl",SysConfigService.class);
		int rows = service.deleteObjects(12,13,14);
		System.out.println("删除了 : "+rows);
	}
	
	@Test
	public void testSaveObject(){
		SysConfigService service = ctx.getBean("sysConfigServiceImpl",SysConfigService.class);
		SysConfig entity = new SysConfig();
		entity.setName("Kevinccc");
		entity.setValue("aaaddd");
		int rowCount = service.saveObject(entity);
		System.out.println("添加的行数 = "+rowCount);
	}
	
	
}
