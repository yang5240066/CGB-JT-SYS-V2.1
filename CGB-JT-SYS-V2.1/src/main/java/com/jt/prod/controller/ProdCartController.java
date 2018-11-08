package com.jt.prod.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.prod.entity.ProdCart;
import com.jt.prod.entity.ProdOrder;
import com.jt.prod.service.ProdCartService;
import com.jt.sys.entity.SysConfig;
import com.mysql.fabric.xmlrpc.base.Array;

@Controller
@RequestMapping("/cart/")
public class ProdCartController {
	
	@Autowired
	private ProdCartService rodCartService;
	
	@RequestMapping("doShoppingCart")
	public String doShoppingCart(){
		return "prod/cart_list";
		
	}
	
	
	@RequestMapping("doShoppingEditUI")
	public String doShoppingEditUI(){
		return "prod/cart_edit";
	}
	
	@RequestMapping("doCartFindObjects")
	@ResponseBody
	public JsonResult doCartFindObjects(HttpServletRequest request,Integer id){
		HttpSession session = request.getSession();
		int attribute = (int)session.getAttribute("userId");
		List<ProdCart> doFindObjects = rodCartService.doFindObjects(attribute);
		return new JsonResult(doFindObjects);
	}
	
	@RequestMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(Integer...ids){
		System.out.println(Arrays.toString(ids));
		rodCartService.deleteObjects(ids);
		return new JsonResult("删除成功！");
		
	}
	
	@RequestMapping(value="doFindPageObjects"
			,produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doFindPageObjects(
			Integer userId,
			Integer pageCurrent){
		//System.out.println(pageCurrent+userId);
		return new JsonResult(rodCartService.findPageObjects(userId, pageCurrent));
	}
	
	@RequestMapping(value="doSaveObject")//默认是7种请求方式
	@ResponseBody 
	public JsonResult doSaveObject(String receiverName,String address,String receiverMobile,Integer... id){
		//System.out.println(entity);
		rodCartService.insertObject(receiverName,address,receiverMobile,id);
		JsonResult jsonResult = new JsonResult("save ok");
		return jsonResult;
	}
	
	//加入购物车
	@RequestMapping(value="doInsertWuPin"
			/*,consumes="application/json;charset=utf-8"*/)
	@ResponseBody
	public JsonResult doInsertWuPin(ProdCart entity){
		rodCartService.insertWuPin(entity);
		System.out.println("doUpdateObject.entity = "+entity);
		JsonResult jsonResult = new JsonResult("添加成功");
			  return jsonResult;
	}
	
	//减少物品
		@RequestMapping(value="doDeletWuPin"
				/*,consumes="application/json;charset=utf-8"*/)
		@ResponseBody
		public JsonResult doDeletWuPin(ProdCart entity){
			rodCartService.deletWuPin(entity);
			System.out.println("doUpdateObject.entity = "+entity);
			JsonResult jsonResult = new JsonResult("移除成功");
				  return jsonResult;
		}
	
}






