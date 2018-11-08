package com.jt.prod.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.exception.ServiceException;
import com.jt.prod.dao.ProdOrderDao;
import com.jt.prod.dao.ProdRepertoryDao;
import com.jt.prod.dao.ProdWalletDao;
import com.jt.prod.entity.ProdOrder;
import com.jt.prod.entity.ProdWallet;
import com.jt.prod.service.ProdWalletService;

@Service
public class ProdWalletServiceImpl implements ProdWalletService {

	@Autowired
	private ProdWalletDao prodWalletDao;
	
	@Autowired
	private ProdOrderDao prodOrderDao;
	
	@Autowired
	private ProdRepertoryDao prodRepertoryDao;
	
	@Override
	public ProdWallet findProdWalletByUserId(Integer userId) {
		ProdWallet pw=null;
		if(!checkIsHasProdWallet(userId)) {
			insertProdWallet(userId);
		}
		pw = prodWalletDao.findProdWalletByUserId(userId);
		return pw;
	}

	@Override
	public int updateProdWalletPayPassword(Integer userId, String password) {
		ProdWallet pw=new ProdWallet();
		pw = prodWalletDao.findProdWalletByUserId(userId);
		pw.setPayPassword(password);
		int rows = prodWalletDao.updateProdWalletPayPassword(pw);
		return rows;
	}

	@Override
	public int insertProdWallet(Integer userId) {
		int rows = 0;
		if (!checkIsHasProdWallet(userId)) {
			checkIsHasProdWallet(userId);
			ProdWallet prodWallet = new ProdWallet();
			prodWallet.setUserId(userId);
			prodWallet.setBalance(0.00);
			rows = prodWalletDao.insertProdWallet(prodWallet);
			
		} else {
			throw new IllegalArgumentException("该用户已创建钱包");
		}
		return rows;
	}

	@Override
	public int deleteProdWallet(Integer userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean checkIsHasProdWallet(Integer userId) {
		ProdWallet pw = prodWalletDao.findProdWalletByUserId(userId);
		if (pw != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkIsHasPayPassword(Integer userId) {
		ProdWallet pw = prodWalletDao.findProdWalletByUserId(userId);
		if (pw != null && pw.getPayPassword() != null) {
			return true;
		}
		return false;
	}

	@Override
	public int updateProdWalletPayPassword(Integer userId, String beforePassword, String afterPassword) {
		ProdWallet pw = prodWalletDao.findProdWalletByUserId(userId);
		if(beforePassword==null||(!beforePassword.equals(pw.getPayPassword())))
			throw new IllegalArgumentException("原支付密码不正确");
		if(afterPassword==null)
			throw new IllegalArgumentException("密码不能为空");
		System.out.println(afterPassword);
		pw.setPayPassword(afterPassword);
		int rows = prodWalletDao.updateProdWalletPayPassword(pw);
		return rows;
	}

	@Override
	public ProdOrder findOrderByOrderId(Integer id) {
		ProdOrder po = prodOrderDao.findOrderByOrderId(id);
		return po;
	}

	@Override
	public String doPay(Integer userId, Integer orderId, Double price,String password) {
		Double balance = prodWalletDao.findBalanceByUserId(userId);
		if(balance<price)throw new ServiceException("余额不足请充值!");
		ProdWallet pw1 = prodWalletDao.findProdWalletByUserId(userId);
		ProdWallet pw2 = prodWalletDao.findProdWalletByUserId(1);
		String payPassword = pw1.getPayPassword();
		if(password==null||!password.equals(payPassword))
			throw new IllegalArgumentException("支付密码不正确");
		pw1.setBalance(balance-price);
		Double balance2 = pw2.getBalance();
		pw2.setBalance(balance2+price);
		prodWalletDao.updateProdWalletBalance(pw1);
		prodWalletDao.updateProdWalletBalance(pw2);
		prodOrderDao.updateOrderStatusByOrderId(orderId);
		ProdOrder po = findOrderByOrderId(orderId);
		Integer pnum = po.getNum();
		String name = po.getTitle();
		System.out.println(123);
		int pnum0 = prodRepertoryDao.selectPnumByName(name);
		int rows = prodRepertoryDao.updatePnumByName((pnum0-pnum), name);
		return "支付成功";
	}

}
