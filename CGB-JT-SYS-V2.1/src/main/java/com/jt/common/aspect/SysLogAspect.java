package com.jt.common.aspect;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jt.common.annotation.RequestLog;
import com.jt.common.util.IPUtils;
import com.jt.sys.dao.SysLogDao;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jt.common.anno.RequestLog;
//import com.jt.common.util.IPUtils;
//import com.jt.common.util.ShiroUtils;
//import com.jt.sys.dao.SysLogDao;
import com.jt.sys.entity.SysLog;
import com.jt.sys.entity.SysUser;

@Order(1)
@Aspect
@Component
public class SysLogAspect {
	
//	private Logger log=Logger.getLogger(SysLogAspect.class);
	
    @Autowired
	private SysLogDao sysLogDao;
    
	@Pointcut("bean(*ServiceImpl)")
	//可以捕捉所有ServiceImpl结尾的实现类,原来是SysConfigServiceImpl
//	@Pointcut("@annotation(com.jt.common.annotation.RequestLog)")
	public void logPointCut(){}
	
    @Around("logPointCut()")//环绕通知,重点掌握
    public Object around(ProceedingJoinPoint //连接点
    		jointPoint) throws Throwable{
    	Object result = saveObject(jointPoint);
    	return result;
    }

	private Object saveObject(ProceedingJoinPoint jointPoint) throws Throwable {
		//获取方法执行时间长度
		long start = System.currentTimeMillis();
		
		Object proceed = jointPoint.proceed();
		
		long end = System.currentTimeMillis();
		System.out.println("方法执行时长为 : "+(end-start));
		//获取当前操作的用户
		SysUser user =(SysUser)SecurityUtils.getSubject().getPrincipal();
		String username = user.getUsername();
		//更具需求获取操作的方法、传入的参数、ip地址
		MethodSignature ms = (MethodSignature)jointPoint.getSignature();//方法的签名
		Class<?> target = jointPoint.getTarget().getClass();//获取目标对象
		
		System.out.println(target.getClass().getName());
		System.out.println(ms.getParameterTypes());
		
		String clsMethod = target.getName()+"."+ms.getName();//类路径.方法名
		//获取方法是传入的实际参数
		String args = JSON.toJSONString(jointPoint.getArgs());
		System.out.println("arges : "+args);
		//获取当前操作的说明（是什么操作）
		Method declaredMethod = target.getDeclaredMethod(ms.getName(), ms.getParameterTypes());
		System.out.println(declaredMethod);
		RequestLog requestLog = declaredMethod.getDeclaredAnnotation(RequestLog.class);
		String operation = "";
		if(requestLog!=null) {
			operation = requestLog.value();
		}
		
		//获取当前用户的ip地址
		String ipAddr = IPUtils.getIpAddr();
		System.out.println(ipAddr);
		//封装日志信息
		SysLog sysLog = new SysLog();
		
		sysLog.setCreateDate(new Date());
		sysLog.setId(user.getId());
		sysLog.setIp(ipAddr);
		sysLog.setMethod(clsMethod);
		sysLog.setOperation(operation);
		sysLog.setParams(args);
		sysLog.setTime(end-start);
		sysLog.setUsername(username);
		int rows = sysLogDao.insertObject(sysLog);
		return proceed;
	}

/*	private Object saveObject(ProceedingJoinPoint jointPoint) throws Throwable, NoSuchMethodException {
		long startTime=System.currentTimeMillis();
//    	//执行目标方法(result为目标方法的执行结果)
    	Object result=jointPoint.proceed();
    	long endTime=System.currentTimeMillis();
    	//计算执行时长
    	long totalTime=endTime-startTime;
    	//System.out.println(totalTime);
    	
    	//2.获取当前用户
    	SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
    	String username = user.getUsername();
    	
    	//3.获取操作的方法()
    	//3.1获取方法的类名
    	Class<?> targetCls = jointPoint.getTarget().getClass();
    	
    	MethodSignature ms = (MethodSignature)jointPoint.getSignature();
    	//获取方法的参数
    	Class[] parameterTypes = ms.getParameterTypes();
    	String methodName = ms.getName();
    	String s = targetCls.getName()+"."+methodName;
    	//4.获取操作方法时传入的实际参数
    	String args = JSON.toJSONString(jointPoint.getArgs());
    	//5.获取当前操作的说明
    	Method method = targetCls.getDeclaredMethod(methodName, parameterTypes);
    	RequestLog requestLog = method.getDeclaredAnnotation(RequestLog.class);
    	String operation = "";
    	if(requestLog!=null){
    		operation = requestLog.value();
    	}
    	//6.获取当前用户的Ip地址
    	String ip = IPUtils.getIpAddr();
    	//日志信息包括:用户名,方法操作说明,方法报名+类名,方法参数,ip地址,执行时间
    	//7.封装日志信息
    	SysLog log = new SysLog();
    	log.setIp(ip);
    	log.setUsername(username);
    	log.setOperation(operation);
    	log.setMethod(method.getName());
    	log.setTime(totalTime);
    	log.setParams(args);
    	//System.out.println(log);
    	int rows = sysLogDao.insertObject(log);
		return result;
	}*/
    
}
