package com.jt.prod.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.prod.entity.ProdOrder;
import com.jt.prod.service.ProdOrderService;

@Controller
@RequestMapping("/prod/order/")
public class ProdOrderController {
	@Autowired
	private ProdOrderService prodOrderService;
	
	@RequestMapping("doOrderListUI")
	public String doOrderListUI(){
		return "prod/order_list";
	}
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(
			Integer userId,
			String status,
			Integer orderId,
			Integer pageCurrent){
		System.out.println(status);
		PageObject<Map> pageObject 
				= prodOrderService.findPageObjects(userId,status,orderId, pageCurrent);
		return new JsonResult(pageObject);
	}
	

	@RequestMapping("doLoadEditUI")
	public String doUpdateObject(){
		return "prod/order_edit";
	}

	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(ProdOrder entity){
		int saveObject = prodOrderService.saveObject(entity);
		return new JsonResult("save ok");
	}

	
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(
			Integer... orderId){
		System.out.println(44);
		System.out.println(orderId);
		System.out.println(Arrays.toString(orderId));
//		System.out.println(123);
//		System.out.println(orderId.toString());
//		orderId = new Integer[]{5,6};
		prodOrderService.deleteObject(orderId);
		return new JsonResult("删除成功");
	}

	
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(ProdOrder entity){
		prodOrderService.updateObject(entity);
		return new JsonResult("update ok");
	}



}








