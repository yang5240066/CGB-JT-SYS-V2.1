package com.jt.prod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.prod.service.ProdOrderService;
import com.jt.prod.service.ProdWalletService;

@Controller
@RequestMapping("/prod/pay/")
public class ProdPayController {
	
	
	@Autowired
	private ProdWalletService prodWalletService;
	@RequestMapping("doPayUI")
	public String doPayUI() {
		return "pay/user-pay";		
	}
	@RequestMapping("doFindOrderById")
	@ResponseBody
	public JsonResult doFindOrderById(Integer id) {
		return new JsonResult(prodWalletService.findOrderByOrderId(id));
	}
	
	@RequestMapping("doPay")
	@ResponseBody
	public JsonResult doPay(Integer userId,Integer orderId,Double price,String password) {
		return new JsonResult(prodWalletService.doPay(userId,orderId,price,password));
	}
}
