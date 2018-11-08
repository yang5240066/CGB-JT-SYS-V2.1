package com.test;

import java.util.Date;

import org.junit.Test;

import com.jt.prod.dao.ProdRechargeDao;
import com.jt.prod.entity.ProdRecharge;

public class TestProdRecharge extends TestBase {
	@Test
	public void test() {
		ProdRechargeDao bean = ctx.getBean(ProdRechargeDao.class);
		ProdRecharge prodRecharge=new ProdRecharge();
		prodRecharge.setUserId(1);
		Date rechangeTime=new Date();
		prodRecharge.setRechargeTime(rechangeTime);
		prodRecharge.setRechargeAmount(100.00);
		Double beforeBalance = 20.00;
		prodRecharge.setBeforeBalance(beforeBalance);
		Double afterBalance = beforeBalance+100.00;
		prodRecharge.setAfterBalance(afterBalance );
		bean.insertProdRecharge(prodRecharge);
	}
}
