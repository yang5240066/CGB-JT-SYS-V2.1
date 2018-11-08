package com.jt.prod.service;

import com.jt.common.vo.PageObject;
import com.jt.prod.entity.ProdRecharge;

public interface ProdRechargeService {
	PageObject<ProdRecharge> findObjectsByUserId(Integer userId,Integer pageCurrent);
	ProdRecharge findProdRechargeByUserId(Integer userId);
	int updateProdRecharge(ProdRecharge prodRecharge);
	int insertProdRecharge(Integer userId,Double rechargeAmount);
	int deleteProdRecharge(Integer userId);
}
