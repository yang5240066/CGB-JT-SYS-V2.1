package com.jt.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 这里只是进行安全框架的配置
 * 安全框架配置类
 * 定义shiro框架的配置信息
 * @author UID
 */
@Configuration
public class AppShiroConfig {
	
	public AppShiroConfig() {
		//System.out.println("AppShiroConfig.AppShiroConfig()");
	}
	/**
	 * 配置shiro的SecurityManage
	 */
	@Bean
	public SecurityManager newSecurityManager(
			//授权
			AuthorizingRealm realm){//自动去spring容器中取    
		DefaultWebSecurityManager sManage = new DefaultWebSecurityManager();
		sManage.setRealm(realm);
		return sManage;
	}
	
	/**
	  * 配置Shiro的過濾器Bean工廠
	  * @param securityManager
	  * @return
	  */
	 @Bean("shiroFilterFactoryBean")
	 public ShiroFilterFactoryBean newShiroFilterFactoryBean(
//			 此处省略了autowired,Spring会自动从容器中,找到SecurityManager
//			 类型的对象,作为参数传进来
	 			SecurityManager securityManager){//shiro 包
	 		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
	 		
	 		bean.setSecurityManager(securityManager);
	 	    //当此用户是一个非认证用户,需要先登陆进行认证
	 		bean.setLoginUrl("/doLoginUI.do");
	 		//定义请求过滤规则
	 		LinkedHashMap<String,String> fcMap=new LinkedHashMap<>();
	 		fcMap.put("/bower_components/**","anon");//anon表示允许匿名访问
	 		fcMap.put("/build/**", "anon");
	 		fcMap.put("/dist/**","anon");
	 		fcMap.put("/plugins/**","anon");
	 		fcMap.put("/doLogin.do","anon");
	 		fcMap.put("/doLogout.do ","logout");
	 		fcMap.put("/**", "authc");//必须授权才能访问
	 		bean.setFilterChainDefinitionMap(fcMap);
	 		return bean;
	 }
	 
	 //下面三个方法到底是用来干嘛的?
	/***
	  * 配置shiro框架组件的生命周期管理对象
	  * @return
	  */
	 @Bean("lifecycleBeanPostProcessor")
	 public LifecycleBeanPostProcessor newLifecycleBeanPostProcessor(){
	 	return new LifecycleBeanPostProcessor();
	 }

    /**
      * 配置负责为Bean对象(需要授权访问的方法所在的对象)
      * 创建代理对象的Bean组件
      */
	 @DependsOn(value="lifecycleBeanPostProcessor")//指定授权访问的对象
	 @Bean
	 public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator(){
	   return new DefaultAdvisorAutoProxyCreator();
	 }
     /**
      * 配置授权属性应用对象(在执行授权操作时需要用到此对象)
      * @param securityManager
      * @return
      */
	 @Bean
	 public AuthorizationAttributeSourceAdvisor 
	        newAuthorizationAttributeSourceAdvisor(
	 		SecurityManager securityManager){
	 		AuthorizationAttributeSourceAdvisor bean=
	 	    new AuthorizationAttributeSourceAdvisor();
	 		bean.setSecurityManager(securityManager);
	 		return bean;
	 }
	
	
}
