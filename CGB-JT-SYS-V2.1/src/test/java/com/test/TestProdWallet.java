package com.test;

import org.junit.Test;

import com.jt.common.util.QRUtils;
import com.jt.prod.dao.ProdWalletDao;
import com.jt.prod.entity.ProdWallet;
import com.jt.prod.service.ProdWalletService;


public class TestProdWallet extends TestBase {
	@Test
	public void testFindProdWalletByUserId() {
		ProdWalletDao bean = ctx.getBean(ProdWalletDao.class);
		System.out.println(bean);
		ProdWallet findProdWalletByUserId = bean.findProdWalletByUserId(1);
		System.out.println(findProdWalletByUserId);
	}
	@Test
	public void testUpdateProdWalletBalance(){
		ProdWalletDao bean = ctx.getBean(ProdWalletDao.class);
		ProdWallet prodWallet = new ProdWallet();
		prodWallet.setUserId(1);
		prodWallet.setBalance(10.00);
		bean.updateProdWalletBalance(prodWallet);
	}
	@Test
	public void testCheckIsHasProdWallet() {
		ProdWalletService bean = ctx.getBean(ProdWalletService.class);
		boolean pw = bean.checkIsHasProdWallet(3);
		System.out.println(pw);
	}
	@Test
	public void testCheckIsHasPayPassword() {
		ProdWalletService bean = ctx.getBean(ProdWalletService.class);
		boolean pw = bean.checkIsHasPayPassword(2);
		System.out.println(pw);
	}
	@Test
	public void testInsertProdWallet() {
		ProdWalletService bean = ctx.getBean(ProdWalletService.class);
		int pw = bean.insertProdWallet(3);
		System.out.println(pw);
	}
	@Test 
	public void test() throws Exception {
		String createQR = QRUtils.createQR("sadasldkj");
		
	}
}
