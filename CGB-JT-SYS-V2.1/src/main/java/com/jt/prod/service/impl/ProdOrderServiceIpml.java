package com.jt.prod.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jt.common.annotation.RequestLog;
import com.jt.common.exception.ServiceException;
import com.jt.common.vo.PageObject;
import com.jt.prod.dao.ProdOrderDao;
import com.jt.prod.entity.ProdOrder;
import com.jt.prod.service.ProdOrderService;
@Service
@Transactional(rollbackFor=Throwable.class)//直接在类上加事务管理,方法全部会加上(所有的错误都回滚)
public class ProdOrderServiceIpml implements ProdOrderService {
	@Autowired
	private ProdOrderDao prodOrderDao;

	@RequestLog("配置信息分页查询")
	@Transactional(readOnly=true,isolation=Isolation.READ_COMMITTED,timeout=30)//事务锁范围变小,加快速度
	@Override
	public PageObject<Map> findPageObjects(Integer userId,
			String status,Integer orderId, Integer pageCurrent) {
		System.out.println("================userId:"+userId);
		if (pageCurrent==null||pageCurrent<1) {
			throw new IllegalArgumentException("参数不合法");
		}
		int rowCount = prodOrderDao.getRowCount(orderId,userId,status);
		if (rowCount==0) {
			throw new ServiceException("没有查到此条信息!!!");
		}
		int pageSize = 5;
		int startIndex = pageSize*(pageCurrent-1);
		int pageCount = rowCount/pageSize;
		if (rowCount%pageSize!=0) {
			pageCount++;
		}
		List<Map> records
		= prodOrderDao.findPageObjects(userId,status,orderId, startIndex, pageSize);
		PageObject<Map> pageObject = new PageObject<>();
		pageObject.setPageCount(pageCount);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(records);
		return pageObject;
	}

	@Override
	public int saveObject(ProdOrder entity) {
		//1.参数合法验证
		if(entity==null)
			throw new IllegalArgumentException("订单不能为空!");
		//2.将数据写入到数据库
		int rows;
		try{//运维监控
			rows=prodOrderDao.insertObject(entity);
		}catch(Throwable t){
			t.printStackTrace();
			//报警....
			throw new ServiceException("系统故障,正在修复");
		}
		//验证结果
		if(rows==0)
			throw new ServiceException("订单获取失败!");
		//3.返回结果
		return rows;
	}


	@Override
	public int deleteObject(Integer[] orderId) {
		System.out.println(orderId);
		//1.验证参数的有效性
		if (orderId==null||orderId.length==0) {
			throw new IllegalArgumentException("id值无效");
		}
		//2.执行删除操作
		int rows = prodOrderDao.deleteObject(orderId);
		if (rows==0) {
			throw new ServiceException("您要删除的订单已经不存在");
		}
		return rows;
	}


	@Override
	public int updateObject(ProdOrder entity) {
		System.out.println(entity);
		//1.合法性验证
		  if(entity==null)
		  throw new IllegalArgumentException("对象不能为空");
		  if(StringUtils.isEmpty(entity.getTitle()))
		  throw new IllegalArgumentException("商品名称不能为空");
		  System.out.println(entity.getTitle());
		  if(StringUtils.isEmpty(entity.getPrice()))
		  throw new IllegalArgumentException("商品单价不能为空");
		  //2.将数据更新到数据库
		  int rows=prodOrderDao.updateObject(entity);
		  //3.对结果进行验证
		  if(rows==0)
		  throw new ServiceException("此记录可能已经不存在");
		  //4.返回结果(会返回给谁?调用者)
		  return rows;
		
	}

}






















