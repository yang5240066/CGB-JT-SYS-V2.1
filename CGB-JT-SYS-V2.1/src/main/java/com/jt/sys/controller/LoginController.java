package com.jt.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.entity.SysUser;

@RequestMapping("/")
@Controller
public class LoginController {
	
	/*@Autowired
	private SysUserDao sysUserDao;*/
	
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(
			HttpServletRequest request){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		//对用户身份进行认证
		//1.获取主体对象(对此对象进行验证)
		Subject subject = SecurityUtils.getSubject();
		//2.提交用户信息,把用户名和密码封装成token令牌
		UsernamePasswordToken token = new UsernamePasswordToken(
				username,password);		
		//subject把token提交给SecurityManager
		//提交的目的:
		subject.login(token);
		//SysUser find = sysUserDao.findUserByUserName(username);
		//HttpSession session = request.getSession();
		//System.out.println(find.getId());
		//session.setAttribute("userId",find.getId());
		
		//user 对象
		//System.out.println(subject.getPrincipal().toString());
		return new JsonResult("login ok");
	}
}




