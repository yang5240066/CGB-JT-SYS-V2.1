package com.jt.prod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.prod.entity.ProdRepertory;
import com.jt.prod.service.ProdRepertoryService;


@Controller
@RequestMapping("/prod/repertory/")
public class ProdRepertoryController {
	@Autowired
	private ProdRepertoryService prodRepertoryService;
	
	
	
	@RequestMapping("dorepertoryListUI")
	public String dorepertoryListUI(){
		return "prod/repertory_list";
	}
	@RequestMapping("doRepertoryEditUI")
	public String doRepertoryEditUI(){
		return "prod/repertory_edit";
	}
	
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id){
		System.out.println(prodRepertoryService.findObjectById(id));
		return new JsonResult(prodRepertoryService.findObjectById(id));
	}
	
	
	
	
	@RequestMapping(value="doDeleteObjects/{ids}" //rest风格
			,produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doDeleteObjects(
			@PathVariable Integer ids){
		prodRepertoryService.deleteObjects(ids);
		return new JsonResult("delete ok");
	}
	
	//保存数据方法
	@RequestMapping(value="doSaveObject",method=RequestMethod.POST
			,produces="application/json;charset=utf-8")//默认是7种请求方式
	@ResponseBody //produces是让客户端把json串转换成对象
	public JsonResult doSaveObject(ProdRepertory entity){
		prodRepertoryService.saveObject(entity);
		JsonResult jsonResult = new JsonResult("save ok");
		System.out.println(jsonResult);
		return jsonResult;
		//fastjson把服务端的数据转换成json串,再发给客户端
	}
		
		
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
				ProdRepertory entity){//消费数据的时候用
			//将Json格式的串转换成json对象,仅限于post请求
			prodRepertoryService.updateObject(entity);
				  System.out.println("doUpdateObject.entity = "+entity);
				  return new JsonResult("update ok");
		}
		
		@RequestMapping(value="doUpdateObjectById"
				,produces="application/json;charset=utf-8"
				/*,consumes="application/json;charset=utf-8"*/)
		@ResponseBody
		public JsonResult doUpdateObjectById(//客户端向服务端提交的数据是json串
				Integer id){//消费数据的时候用
			//将Json格式的串转换成json对象,仅限于post请求
			prodRepertoryService.updateObjectById(id);
				  System.out.println("doUpdateObject.entity = "+id);
				  return new JsonResult("update ok");
		}
	
	
	
	
	
	
	
	
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String name,Integer pageCurrent){
		return new JsonResult(prodRepertoryService.findPageObjects(name, pageCurrent));
	}
	
	@RequestMapping("doChangeStatusById")
	@ResponseBody
	public JsonResult doChangeStatusById(Integer id,Integer newStatus){
		
		return new JsonResult(prodRepertoryService.updateStatusById(id, newStatus));
	}
	
	
	
	
	
	
	
	
}
