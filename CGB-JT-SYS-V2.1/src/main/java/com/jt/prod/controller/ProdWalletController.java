package com.jt.prod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.prod.service.ProdWalletService;

@Controller
@RequestMapping("/prod/wallet/")
public class ProdWalletController {
	@Autowired
	private ProdWalletService prodWalletService;

	@RequestMapping("doWalletUI")
	public String doWalletUI() {
		return "pay/user-wallet";
	}

	@RequestMapping("doGetWallet")
	@ResponseBody
	public JsonResult doGetWallet(Integer userId) {
		return new JsonResult(prodWalletService.findProdWalletByUserId(userId));
	}

	@RequestMapping("doHistoryUI")
	public String doHistoryUI() {
		return "pay/user-history";
	}

	@RequestMapping("doUpdatePwdUI")
	public String doUpDatePwdUI() {
		return "pay/user-pwd";
	}

	@RequestMapping(value = "doCheckIsHasPayPassword", produces = "application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doCheckIsHasPayPassword(Integer userId) {
		return new JsonResult(prodWalletService.checkIsHasPayPassword(userId));

	}
	
	@RequestMapping("doSetPayPasswordUI")
	public String doSetPayPasswordUI() {
		return "pay/set-pay-password";
		
	}
	
	@RequestMapping(value="doSetPayPassword",produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doSetPayPassword(Integer userId, String password) {
		prodWalletService.updateProdWalletPayPassword(userId, password);
		JsonResult jsonResult = new JsonResult("设置成功");
		  return jsonResult;
	}
	
	@RequestMapping(value="doUpdatePayPassword",produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doUpdatePayPassword(Integer userId, String beforePassword,String afterPassword) {
		prodWalletService.updateProdWalletPayPassword(userId, beforePassword,afterPassword);
		JsonResult jsonResult = new JsonResult("设置成功");
		  return jsonResult;
	}

}
