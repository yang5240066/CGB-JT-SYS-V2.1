package com.jt.prod.service;

import java.util.List;

import com.jt.common.vo.PageObject;
import com.jt.prod.entity.ProdCart;
import com.jt.prod.entity.ProdMenu;
import com.jt.prod.entity.ProdOrder;

public interface ProdCartService {
	
	List<ProdCart> doFindObjects(Integer id);
	
	int deleteObjects(Integer... ids);
	
	PageObject<ProdCart> findPageObjects(
			Integer userId,
			Integer pageCurrent);
	
	int insertObject(String receiverName,String address,String receiverMobile,Integer... id);
	
	//添加物品
	int insertWuPin(ProdCart entity);
	
	//移除物品
	int deletWuPin(ProdCart entity);
}
