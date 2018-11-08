package com.test;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jt.common.config.AppRootConfig;

public class TestBase {
	
	protected AnnotationConfigApplicationContext ctx;
	
	@Before
	public void init(){
		ctx = new AnnotationConfigApplicationContext(AppRootConfig.class);	
	}
	
	@After
	public void after(){
		ctx.close();
	}
	
	
}
