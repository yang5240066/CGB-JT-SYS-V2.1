package com.jt.prod.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.prod.entity.ProdRecharge;

public interface ProdRechargeDao {
	List<ProdRecharge> findObjectsByUserId(
			@Param("userId")Integer userId);
	int updateProdRecharge(ProdRecharge prodRecharge);
	int insertProdRecharge(ProdRecharge prodRecharge);
	int deleteProdRecharge(ProdRecharge prodRecharge);
	int getRowCount(Integer userId);
}
