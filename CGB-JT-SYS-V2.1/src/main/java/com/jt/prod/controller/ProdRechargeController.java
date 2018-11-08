package com.jt.prod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.util.QRUtils;
import com.jt.common.vo.JsonResult;
import com.jt.prod.service.ProdRechargeService;

@Controller
@RequestMapping("/prod/recharge/")
public class ProdRechargeController {

	@Autowired
	ProdRechargeService prodRechargeService;

	@RequestMapping("doRechargeUI")
	public String doRechargeUI() {
		return "pay/user-recharge";
	}

	@RequestMapping("doGetQR")
	@ResponseBody
	public JsonResult doGetQR(Double rechargeAmount) throws Exception {
		String createQR = QRUtils.createQR("充值" + rechargeAmount + "元");
		return new JsonResult(createQR);
	}

	@RequestMapping(value="doRecharge",produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doRecharge(Double rechargeAmount, Integer userId) {
		prodRechargeService.insertProdRecharge(userId, rechargeAmount);
		JsonResult jsonResult = new JsonResult("支付成功");
		return jsonResult;
	}
	
	@RequestMapping(value="doFindPageObjects"
			,produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doFindPageObjects(
			Integer userId,
			Integer pageCurrent){
		return new JsonResult(prodRechargeService.findObjectsByUserId(userId, pageCurrent));
	}
}
