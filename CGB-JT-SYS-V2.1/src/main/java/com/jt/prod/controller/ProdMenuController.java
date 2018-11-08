package com.jt.prod.controller;


import java.util.List;

import org.apache.poi.hssf.extractor.ExcelExtractor;

import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.ExcelOperate;
import com.jt.common.vo.JsonResult;
import com.jt.prod.entity.ProdCart;
import com.jt.prod.entity.ProdMenu;
import com.jt.prod.entity.ProdRepertory;
import com.jt.prod.service.ProdMenuService;
/**
 * 商品菜单控制器
 * @author UID
 *
 */
@Controller
@RequestMapping("/prod/menu/")
public class ProdMenuController {

	@Autowired
	private ProdMenuService productService;
	
	 
		@RequestMapping("doProdListUI")
		public String doConfigListUI(){
			return "prod/menu_list";
		}
		//返回添加编辑页面的方法
		@RequestMapping("doMenuEditUI")
		public String doConfigEditUI(){
			return "prod/menu_edit";
		}
						
		@RequestMapping(value="doFindPageObjects"
				,produces="application/json;charset=utf-8")
		@ResponseBody
		public JsonResult doFindPageObjects(
				String name,
				Integer pageCurrent){
			return new JsonResult(productService.findPageObjects(name, pageCurrent));
		}//将对象转换为Json输出(fastjson),方法运行时底层会将返回的对象转换为json串
		
		//删除操作的方法
		@RequestMapping(value="doDeleteObjects/{ids}" //rest风格
				,produces="application/json;charset=utf-8")
		//设置响应头,把串转换成对象
		@ResponseBody
		public JsonResult doDeleteObjects(
				@PathVariable Integer... ids){
//			System.out.println(44);
//			System.out.println(ids);
//			System.out.println(Arrays.toString(ids));
			productService.deleteObjects(ids);
			return new JsonResult("delete ok");
		}
		
		//保存数据方法
		@RequestMapping(value="doSaveObject",method=RequestMethod.POST
				,produces="application/json;charset=utf-8")//默认是7种请求方式
		@ResponseBody //produces是让客户端把json串转换成对象
		 public JsonResult doSaveObject(ProdMenu entity){
			productService.saveObject(entity);
			JsonResult jsonResult = new JsonResult("save ok");
				  return jsonResult;
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
				ProdMenu entity){//消费数据的时候用
			//将Json格式的串转换成json对象,仅限于post请求
			productService.updateObject(entity);
			//System.out.println("doUpdateObject.entity = "+entity);
			return new JsonResult("update ok");
		}


		//保存数据方法
		@RequestMapping(value="doExcel")//默认是7种请求方式
		@ResponseBody 
		public JsonResult doExcel(String excelUrl){
			//System.out.println(excelUrl);
			List<ProdMenu> list = productService.findAllPageObjects();
			System.out.println(list);
			System.out.println(list.size());
			ExcelOperate.createExcel(list, excelUrl);
			 return new JsonResult("生产excel成功");
		}
		

		//加入购物车
				@RequestMapping(value="doInsertWuPin"
						
						/*,consumes="application/json;charset=utf-8"*/)
				@ResponseBody
				public JsonResult doInsertWuPin(ProdCart entity){
					productService.insertWuPin(entity);
					System.out.println("doUpdateObject.entity = "+entity);
					JsonResult jsonResult = new JsonResult("添加成功");
						  return jsonResult;
				}

}
