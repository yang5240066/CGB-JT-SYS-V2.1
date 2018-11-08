package com.jt.common.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * spring-mvc配置类
 * spring-mvc.xml
 */
//不写Configuration,否则AppRootConfig会扫描到这个mvc的配置类
@ComponentScan(value="com.jt",
includeFilters={
@Filter(type=FilterType.ANNOTATION,classes={Controller.class,ControllerAdvice.class})},
useDefaultFilters=false)
@EnableWebMvc//启动mvc默认配置
public class AppServletConfig extends WebMvcConfigurerAdapter{/*重写此类的方法可以配置视图解析器*/

	/*配置视图解析器*/
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/pages/", ".html");
	}
	
	//FastJson转换器
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//1.构建fastjson转换器对象
		FastJsonHttpMessageConverter msConverter=new FastJsonHttpMessageConverter();
		//配置MessageConverter对象
		FastJsonConfig config = new FastJsonConfig();
		//解决反序列化漏洞
		config.setSerializeConfig(SerializeConfig.globalInstance);
		
		//禁用循环引用问题(id)
		config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
		msConverter.setFastJsonConfig(config);
		
		//2.设置转换器对象对媒体的支持
		List<MediaType> list = new ArrayList<>();
		list.add(new MediaType("text","html",Charset.forName("utf-8")));
		list.add(new MediaType("application","json",Charset.forName("utf-8")));
		msConverter.setSupportedMediaTypes(list);
		//3.添加转换器对象
		converters.add(msConverter);
	}	
	
	
	
	
}







