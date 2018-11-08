package com.test;

import org.junit.Test;

import com.jt.common.vo.PageObject;
import com.jt.prod.entity.ProdMenu;
import com.jt.prod.service.ProdMenuService;
import com.jt.sys.entity.SysConfig;
import com.jt.sys.service.SysConfigService;

public class TestProdMenuService extends TestBase{
	
	@Test
	public void testFindPageObjects(){
		ProdMenuService service = ctx.getBean(ProdMenuService.class);
		PageObject<ProdMenu> pageObject = service.findPageObjects("小", 1);
		System.out.println(pageObject);
	}
	
	@Test
	public void testDeleteObjects(){
		ProdMenuService service = ctx.getBean(ProdMenuService.class);
		int rows = service.deleteObjects(1,3,4);
		System.out.println("删除了 : "+rows);
	}
	
	@Test
	public void testSaveObject(){
		ProdMenuService service = ctx.getBean(ProdMenuService.class);
		ProdMenu entity = new ProdMenu();
		entity.setName("小红帽2");
		entity.setPrice(11.9);
		int rowCount = service.saveObject(entity);
		System.out.println("添加的行数 = "+rowCount);
	}
	
	
}
