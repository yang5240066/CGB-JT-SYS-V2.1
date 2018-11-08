package com.jt.prod.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jt.prod.entity.ProdWallet;

public interface ProdWalletDao {
	//@Select("select * from prod_wallet where userId = #{userId}")
	ProdWallet findProdWalletByUserId(@Param("userId")Integer userId);
	int updateProdWalletBalance(ProdWallet prodWallet);
	int updateProdWalletPayPassword(ProdWallet prodWallet);
	int insertProdWallet(ProdWallet prodWallet);
	int deleteProdWallet(ProdWallet prodWallet);
	Double findBalanceByUserId(Integer userId);
}
