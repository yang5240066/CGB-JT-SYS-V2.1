package com.jt.common.config;


import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
/**
 * web.xml注解启动类
 * tomcat 启动时会自动加载此对象,
 * 并执行此对象的onStartUp方法
 * @author yang
 */
public class AppWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		//此方法中会自动注册前端控制器
//		super.onStartup(servletContext);//就是注册监听器和前端控制器
		//注册监听器(调用父类方法)
		registerContextLoaderListener(servletContext);//执行完这局执行getRootConfigClasses
//		注册shiro过滤器
		registerShiroFiler(servletContext);
//		注册servlet前端控制器(调用父类方法)
		registerDispatcherServlet(servletContext);
	}
	
	private void registerShiroFiler(ServletContext servletContext) {
		//注册过滤器,web.xml上类似于注册前端控制器
		Dynamic dFilter = servletContext.addFilter(
				"filterProxy", DelegatingFilterProxy.class);
		//初始化参数,这里需要一个shiroFilterFactory
		dFilter.setInitParameter("targetBeanName"//DelegatingFilterProxy的参数
				,"shiroFilterFactoryBean");//这个值必须与spring管理的shiro中的bean相同
		//过滤器的映射路径
		dFilter.addMappingForUrlPatterns(
				null, //dispatcherTypes,不写默认是所有的
				false, //isMatchAfter
				"/*");
	}

	//认证属于后端业务,初始化放在springmvc之后
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{AppRootConfig.class,AppShiroConfig.class};
	}
	/**如下方法中要加载MVC配置*/
	@Override
	protected Class<?>[] getServletConfigClasses() {
		System.out.println("WebAppInitializer.getServletConfigClasses()");
		return new Class[]{AppServletConfig.class};
	}
	/**如下方法配置映射路径*/
	@Override
	protected String[] getServletMappings() {
		System.out.println("WebAppInitializer.getServletMappings()");
		return new String[]{"*.do"};
	}

}
