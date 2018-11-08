package com.jt.common;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;

/**
 * 全局异常处理类(需要使用@ControllerAdvice注解)
 * 可以此类中添加所有Controller中需要共享的异常处理方法
 * @author UID
 * 400 404 405 406
 */
/*
 * 控制层异常处理类,如果,Controller类中有异常处理方法,那么就近原则,直接
 * 调用异常处理方法处理异常,如果Controller类中没有该异常处理方法,才会到
 * 全局控制层异常处理类中寻找异常处理方法处理异常,如果还没有,就抛给前端
 * 控制器,前端控制器也没有异常处理方法,就直接抛给浏览器/用户
 */
@ControllerAdvice//告诉spring-mvc,这是一个全局异常处理类,你帮我管理
public class GlobalExceptionHandler {
	/**
	 * @ExceptionHandler 此注解声明的方法为一个异常处理方法
	 * @return Class[]
	 */
	@ExceptionHandler(Exception.class)//这个方法可以处理的异常
	@ResponseBody//把JsonResult对象转换成json串
	//这个参数的异常要比上面的大,才能处理,这个是上面的父类.
	public JsonResult doHandleException(Exception e){
//		System.out.println(1111);
		e.printStackTrace();
		return new JsonResult(e);
	}
	
	@ExceptionHandler(RuntimeException.class)//这个方法可以处理的异常
	@ResponseBody//把JsonResult对象转换成json串
	public JsonResult doRuntimeException(RuntimeException e){
		//System.out.println("GlobalExceptionHandler.doRuntimeException()");
		e.printStackTrace();
		return new JsonResult(e);
	}
	
	@ExceptionHandler(ShiroException.class)//这个方法可以处理的异常
	@ResponseBody//把JsonResult对象转换成json串
	public JsonResult doShiroException(ShiroException e){
		e.printStackTrace();
		JsonResult result = new JsonResult();
		result.setState(0);
		if(e instanceof IncorrectCredentialsException){
			result.setMessage("密码不正确");
			return result;
		}
		if(e instanceof AuthorizationException){
			result.setMessage("没权限执行此操作");
			return result;
		}
		result.setMessage(e.getMessage());
		e.printStackTrace();
		return new JsonResult(e);
	}
	
	
	
	
}
