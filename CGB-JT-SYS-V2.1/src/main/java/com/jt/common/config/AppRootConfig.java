package com.jt.common.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
/**
 * 8.20晚做的项目
 * 这是基于全注解的方式整合SSM
 * 过程中出现了几个bug
 * bug1:配置文件包config中的配置文件一开始写成了2份,把配置Mybatis
 * 的配置文件和配置mvc的文件写在了一起,然后加载到spring容器中进行
 * 单元测试
 * bug2:创建SqlSessionFactory的时候classpath*:mapper/sys/*Mapper.xml
 * 映射文件路径和目录一一对应会报错,而在目录中删除sys中间层目录,恢复正常,
 * 即mapper/SysConfigMapper.xml;但在classpath中再删除sys,即
 * classpath*:mapper/*Mapper.xml,又报错,最终,解决办法是修改项目名,恢复正常
 * bug3:之前在CGB-SPRING-IOC-04_redo中的Dao接口的方法
 * SysConfig findById(Integer id)中加了一条sql语句
 * @Select("select * from sys_configs where id = #{id}")
 * 就替代了SysConfigMapper.xml映射文件,而SqlSessionFactory中
 * classpath的配置就可以注释掉,结果我就搞不清为什么之前
 * CGB-SPRING-IOC-04_redo中为什么可以不需要classpath配置.
 * 这个bug说明了注释的重要性.
 * @author UID
 *
 */
//@Configuration
@EnableAspectJAutoProxy//启用AOP的注解
@ComponentScan(value="com.jt",
excludeFilters={@Filter(type=FilterType.ANNOTATION,classes={Controller.class,ControllerAdvice.class})})
@PropertySource("classpath:configs.properties")
@MapperScan(basePackages="com.jt.**.dao")
@EnableTransactionManagement//启用事务驱动的注解
public class AppRootConfig {
	/**
	  * 让系统支持多个properties文件应用
	  * @return
	  */
	 @Bean
	 public PropertySourcesPlaceholderConfigurer newPropertyPlaceholderConfigurer(){
		 return new PropertySourcesPlaceholderConfigurer();
	 }
	
	@Bean()
	public DataSource newDruidDataSource(
			@Value(value = "${jdbcDriver}")String driver,
			@Value(value = "${jdbcUrl}")String url,
			@Value(value = "${jdbcUser}")String user,
			@Value(value = "${jdbcPassword}")String password
			){
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(password);
		return ds;
	}
	
	@Bean("123")
	public SqlSessionFactoryBean newSqlSessionFactory(@Autowired DataSource dataSource) throws Exception{
		SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
		ssfb.setDataSource(dataSource);
		Resource[] resources =  new 
				PathMatchingResourcePatternResolver().
	getResources("classpath*:mapper/**/*Mapper.xml");
//		System.out.println(resources);
		//在AppRootConfig中PageHelper控件实现分页处理
		ssfb.setMapperLocations(resources);
		Interceptor interceptor = new PageInterceptor();
		Properties properties = new Properties();
		properties.put("helperDialect", "mysql");
		interceptor.setProperties(properties );
		Interceptor[] plugins = {interceptor };
		ssfb.setPlugins(plugins );
		return ssfb;
	}
	
//	@Bean
//	public MapperScannerConfigurer newMapperScanner(){
//		MapperScannerConfigurer mapperScanner = new MapperScannerConfigurer();
//		mapperScanner.setSqlSessionFactoryBeanName("123");
////		mapperScanner.setSqlSessionFactory(ssf);
//		mapperScanner.setBasePackage("com.jt.sys.dao");
//		return mapperScanner;
//	}
	
	/**
	 * Spring中的声明式事务控制,主要
	 */
	@Bean("txManager")
	public DataSourceTransactionManager newDataSourceTransactionManager(
			DataSource dataSource){//javax.sql.DataSource
		//创建事务管理对象
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
//		注入数据源对象(Druid)
		tm.setDataSource(dataSource);
		return tm ;
	}
	
}
