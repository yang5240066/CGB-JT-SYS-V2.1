package com.jt.sys.controller;


import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;

@RequestMapping("/")
@Controller
public class PageController {
	
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("doIndexUI")
	public String doIndexUI(){
		return "starter";//starter.html
	}
	@RequestMapping("doPageUI")
	public String doPageUI(){
		return "common/page";
	}
	/**
	 * 返回登录页面
	 * @return
	 */
	@RequestMapping("doLoginUI")
	public String doLoginUI(){
		return "login";
	}
	
	//在页面上显示用户名的方法
	@RequestMapping("doSetUserName")
	@ResponseBody
	public SysUser doSetUserName(){
		//1.获取主体对象(对此对象进行验证)
		SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
		//2.把用户名返回给调用的ajax方法
		System.out.println("user : "+user);
		return user;
	}
	
	//在页面上根据用户权限动态显示菜单
	@RequestMapping("doShowMenus")
	@ResponseBody
	public Integer[] doShowMenus(Integer userId){
		//System.out.println("id = "+userId);
		Integer[] menuIds = sysUserService.findMenuIdsByUserId(userId);
		//System.out.println("menuIds : "+Arrays.toString(menuIds));
		return menuIds;
	}	
	
}




