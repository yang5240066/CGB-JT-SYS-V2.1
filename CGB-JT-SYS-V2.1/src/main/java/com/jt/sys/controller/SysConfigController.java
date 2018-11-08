package com.jt.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.sys.entity.SysConfig;
import com.jt.sys.service.SysConfigService;

@Controller
@RequestMapping("/config/")
public class SysConfigController {

	@Autowired
    private SysConfigService sysConfigService;
    
	@RequestMapping("doConfigListUI")
	public String doConfigListUI(){
		return "sys/config_list";
	}
	
//	http://localhost:8080/CGB-JT-SYS-V1.01/config/doFindPageObjects.do?pageCurrent=1&name=o
	@RequestMapping(value="doFindPageObjects"
			//告诉ajax这是Json串,自动转换成对象
			//ajax的这个	dataType:"json"
			,produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doFindPageObjects(
			String name,
			Integer pageCurrent){
		return new JsonResult(sysConfigService.findPageObjects(name, pageCurrent));
		
//		PageObject<SysConfig> pageObject = sysConfigService.findPageObjects(name, pageCurrent);
//		JsonResult jsonResult = new JsonResult(pageObject);
//		System.out.println(jsonResult);
//		return jsonResult;
	}//将对象转换为Json输出(fastjson),方法运行时底层会将返回的对象转换为json串
	
	//删除操作的方法
	@RequestMapping(value="doDeleteObjects/{ids}" //rest风格
			,produces="application/json;charset=utf-8")
	//设置响应头,把串转换成对象
	@ResponseBody
	public JsonResult doDeleteObjects(
			@PathVariable Integer... ids){
		sysConfigService.deleteObjects(ids);
		return new JsonResult("delete ok");
	}
	
	//返回添加编辑页面的方法
	@RequestMapping("doConfigEditUI")
	public String doConfigEditUI(){
		return "sys/config_edit";
	}
	
	//保存数据方法
	@RequestMapping(value="doSaveObject",method=RequestMethod.POST
			,produces="application/json;charset=utf-8")//默认是7种请求方式
	@ResponseBody //produces是让客户端把json串转换成对象
	 public JsonResult doSaveObject(SysConfig entity){
			  sysConfigService.saveObject(entity);
			  JsonResult jsonResult = new JsonResult("save ok");
			  System.out.println(jsonResult);
			  return jsonResult;
//fastjson把服务端的数据转换成json串,再发给客户端
	}
	
	/*@RequestMapping(value="doSaveObject",method=RequestMethod.POST
			,consumes="application/json;charset=utf-8"
			,produces="application/json;charset=utf-8")//默认是7种请求方式
	@ResponseBody //produces是让客户端把json串转换成对象
	 public JsonResult doSaveObject(
			 @RequestBody SysConfig entity){
			  sysConfigService.saveObject(entity);
			  JsonResult jsonResult = new JsonResult("save ok");
			  System.out.println(jsonResult);
			  return jsonResult;
//fastjson把服务端的数据转换成json串,再发给客户端
	}*/
	
	/**
	 * 修改
	 *  @RequestBody的应用,
	 *  1)处理post请求,并且必须设置post请求的ContentType:"application/json"
	 *  例如客户端向服务端传递的是Jsong格式的字符串,
	 *  服务端需要将这个串转换成java对象
	 *  
	 * @param entity
	 * @return
	 */
	@RequestMapping(value="doUpdateObject"
			,produces="application/json;charset=utf-8"
			/*,consumes="application/json;charset=utf-8"*/)
	@ResponseBody
	public JsonResult doUpdateObject(//客户端向服务端提交的数据是json串
			 SysConfig entity){//消费数据的时候用
		//将Json格式的串转换成json对象,仅限于post请求
			  sysConfigService.updateObject(entity);
			  System.out.println("doUpdateObject.entity = "+entity);
			  return new JsonResult("update ok");
	}

}


