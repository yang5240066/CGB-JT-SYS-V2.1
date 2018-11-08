package com.jt.prod.service;

import java.util.Map;


import com.jt.common.vo.PageObject;
import com.jt.prod.entity.ProdOrder;

public interface ProdOrderService {
	
	int updateObject(ProdOrder entity);
	
	int saveObject(ProdOrder entity);
	

	/**
	 * 基于菜单id执行菜单的删除业务
	 * @param id
	 * @return
	 */
	int deleteObject(Integer[] orderId);

	PageObject<Map> findPageObjects(Integer userId,
			String status,
			Integer orderId,
			Integer pageCurrent);
	

}
