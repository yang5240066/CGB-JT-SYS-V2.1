package com.jt.prod.service;

import com.jt.prod.entity.ProdOrder;
import com.jt.prod.entity.ProdWallet;

public interface ProdWalletService {
	ProdWallet findProdWalletByUserId(Integer userId);
	int insertProdWallet(Integer userId);
	int deleteProdWallet(Integer userId);
	int updateProdWalletPayPassword(Integer userId, String password);
	boolean checkIsHasProdWallet(Integer userId);
	boolean checkIsHasPayPassword(Integer userId);
	int updateProdWalletPayPassword(Integer userId, String beforePassword, String afterPassword);
	ProdOrder findOrderByOrderId(Integer id);
	String doPay(Integer userId, Integer orderId, Double price,String password);
}
