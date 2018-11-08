package com.jt.sys.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.jt.common.util.ShiroUtils;
import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysMenuDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
import com.jt.sys.vo.SysUserDeptResult;
@RequestMapping("/user/")
@Controller
public class SysUserController {
	
	   @Autowired
	   private SysUserService sysUserService;
	   
       @RequestMapping("doUserListUI")
	   public String doUserListUI(){
		   return "sys/user_list";
	   }
       @RequestMapping("doUserEditUI")
       public String doUserEditUI(){
    	   return "sys/user_edit";
       }
      
       //查所有的用户,呈现数据
       @RequestMapping("doFindPageObjects")
       @ResponseBody
       public JsonResult doFindPageObjects(
    		   String username,Integer pageCurrent){
    	   PageObject<SysUserDeptResult>
    	   pageObject=sysUserService.findPageObjects(username,
    					   pageCurrent);
    	  // System.out.println("pageObject = "+pageObject);
    	   return new JsonResult(pageObject);
       }
       
       //删除,不直接删除用户,设置启用,禁用,  禁用就相当于删除
//     @RequestMapping("doValidById")
//     @ResponseBody
//     public JsonResult doValidById(
//  		   Integer id,Integer valid){
////  	   SysUser user=
////  	   (SysUser)SecurityUtils.getSubject().getPrincipal();
//  	   sysUserService.validById(id,valid,"admin");
//  	   return new JsonResult("update ok");
//     }
     @RequestMapping("doValidById")
     @ResponseBody
     public JsonResult doValidById(Integer id,Integer valid){
//  	   用户登录成功以后会自动将用户信息存入session对象
//  	   System.out.println(sysUserService.getClass().getName());
  	   SysUser user=(SysUser)SecurityUtils.getSubject().getPrincipal();
  	   sysUserService.validById(id,valid,user.getUsername());
  	   return new JsonResult("update ok");
     }
     
   //增加
     @RequestMapping("doSaveObject")
     @ResponseBody
     public JsonResult doSaveObject(
  		   SysUser entity,
  		   Integer[]roleIds){
//roleIds如何获取的,根据页面checkbox的勾选数据
//    	 跟用户的权限有关
//  	  SysUser user=ShiroUtils.getPrincipal();
//  	  entity.setCreatedUser(user.getUsername());
//  	  entity.setModifiedUser(user.getUsername());
  	  sysUserService.saveObject(entity,roleIds);
  	  return new JsonResult("save ok");
     }

     //修改
  	  @RequestMapping("doUpdateObject")
  	  @ResponseBody
  	  public JsonResult doUpdateObject(
  			  SysUser entity,
  			  Integer[]roleIds){
  		  sysUserService.updateObject(entity,
  				  roleIds);
  		  return new JsonResult("update ok");
  	  }
     
//		通过id查单个的项目,修改的时候,
//       用来查找该用户所在部门名字和对应角色id用的
   	  @RequestMapping("doFindObjectById")
   	  @ResponseBody
   	  public JsonResult doFindObjectById(Integer id){
   		Map<String,Object> map=
   		sysUserService.findObjectById(id);
   		return new JsonResult(map);
   	  }
    
   	  
   	  
}




