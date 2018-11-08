package com.jt.prod.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jt.prod.entity.ProdOrder;




public interface ProdOrderDao {
	
	int updateObject(ProdOrder entity);
	@Update("update prod_order set status='已付款' where orderId=#{orderId}")
	int updateOrderStatusByOrderId(@Param("orderId") Integer orderId);
	int insertObject(ProdOrder entity);
	
	List<Map> findPageObjects(
			@Param("userId") Integer userId,
			@Param(value="status") String status,
			@Param(value = "orderId") Integer orderId,
			@Param(value = "startIndex") Integer startIndex,
			@Param(value = "pageSize") Integer pageSize);
	int getRowCount(@Param("orderId") Integer orderId,
			@Param("userId") Integer userId,
			@Param("status") String status);
			
	
	/**
	  * 根据id 删除菜单
	  * @param id
	  * @return
	  */
	int deleteObject(@Param("ids")Integer... ids); 
	
	int getRowCountUser(@Param("orderId") Integer orderId,
			@Param("userId") Integer userId);
	@Select("select * from prod_order where orderId=#{orderId}")
	ProdOrder findOrderByOrderId(@Param("orderId")Integer orderId); 
}
