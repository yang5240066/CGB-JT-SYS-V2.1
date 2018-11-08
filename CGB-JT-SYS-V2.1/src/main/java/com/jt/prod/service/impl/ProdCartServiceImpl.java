package com.jt.prod.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.PageObject;
import com.jt.prod.dao.ProdCartDao;
import com.jt.prod.dao.ProdOrderDao;
import com.jt.prod.entity.ProdCart;
import com.jt.prod.entity.ProdOrder;
import com.jt.prod.service.ProdCartService;


@Service
public class ProdCartServiceImpl implements ProdCartService{
	
	@Autowired
	private ProdCartDao prodCartDao;
	@Autowired
	private ProdOrderDao prodOrderDao;
	@Override
	public List<ProdCart> doFindObjects(Integer id) {
		if(id==null || StringUtils.isEmpty(id))
			throw new IllegalArgumentException("您购物车还没有记录");
		
		List<ProdCart> doFindObjects = prodCartDao.doFindObjects(id);
		
		return doFindObjects;
	}

	@Override
	public int deleteObjects(Integer... ids) {
		//1.判定参数合法性,StringUtils是Spring的
//		if(StringUtils.isEmpty(ids))
		if(ids==null||ids.length==0)
			throw new IllegalArgumentException("请选择一个");
		//2.执行删除操作
		int rows;
		try{//数据库是专门的服务器,tomcat也是一台专门的服务器
			rows=prodCartDao.deleteObjects(ids);
		}catch(Throwable e){//可能是异常也可能是错误,Error,Exception
			e.printStackTrace();
			//发出报警信息(例如给运维人员发短信,或者切换到另一台服务器)
			throw new ServiceException("系统故障，正在恢复中...");
		}
		//4.对结果进行验证
		if(rows==0)
			throw new ServiceException("记录可能已经不存在");
		//5.返回结果
		return rows;
	}

	@Override
	public PageObject<ProdCart> findPageObjects(Integer userId, Integer pageCurrent) {
		//1.参数有效性验证(pageCurrent)
				if(pageCurrent==null||pageCurrent<1)//顺序不能变
					throw new IllegalArgumentException("当前页码值无效");

				//2.基于name参数查询总记录数
				int rowCount = 0;
				try {
					rowCount = prodCartDao.getRowCount(userId);
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException("系统故障,稍后访问.");//数据库问题
				}

				//3.对总记录数进行判定,假如值0,无需执行后续查询
				//1,方便定位错误,2,用户体验友好
				if(rowCount==0)
					throw new ServiceException("查询 : 不存在对应记录");

				//4.依据条件查询当前页记录(List)
				//4.1定义页面大小
				int pageSize = 4;
				//4.2计算当前页记录的起始位置
				int startIndex = (pageCurrent-1)*pageSize;
				//4.3从当前页起始位置开始查询当前页记录
				List<ProdCart> records = prodCartDao.findPageObjects(userId, startIndex, pageSize);

				//5.对查询的结果进行封装
				PageObject<ProdCart> pageObject = new PageObject<>();//jdk1.7以后才允许
				pageObject.setRecords(records);//数据
				pageObject.setRowCount(rowCount);//总页数
				pageObject.setPageCurrent(pageCurrent);//当前页
				pageObject.setPageSize(pageSize);//页面大小
				//计算总页数
				int pageCount = rowCount/pageSize;
				if(rowCount%pageSize!=0){
					pageCount++;
				}
				//pageCount = (rowCount-1)/pageSize+1
				pageObject.setPageCount(pageCount);//总页数

				//6.返回结果
				System.out.println(pageObject);
				return pageObject;
		
	}

	@Override
	public int insertObject(String receiverName,String address,String receiverMobile,Integer... id) {
		//1.参数合法验证
				if(StringUtils.isEmpty(receiverName))
					throw new IllegalArgumentException("收货人不能为空");
				if(StringUtils.isEmpty(address))
					throw new IllegalArgumentException("收货地址不能为空 ");
				if(StringUtils.isEmpty(receiverMobile))
					throw new IllegalArgumentException("联系方式不能为空");
				if(receiverMobile.length()<11){
					throw new IllegalArgumentException("手机格式不正确");
				}		
				
				int rows =1;
				ProdOrder entity = null;  
					for(int i=0;i<id.length;i++){
						entity =new ProdOrder();
						System.out.println(id[i]);
						ProdCart objectId = prodCartDao.getObjectId(id[i]);
									
				entity.setUserId(objectId.getUserId());
				entity.setTitle(objectId.getName());
				entity.setNum(objectId.getNumber());
				entity.setPrice(objectId.getPrice());
				entity.setTotalFee(objectId.getPrice()*objectId.getNumber());
				entity.setReceiverName(receiverName);
				entity.setReceiverAddress(address);
				entity.setReceiverMobile(receiverMobile);
					
				/*System.out.println("objectId = "+objectId);
				System.out.println("entity = "+entity);*/
				//2.将数据写入到数据库
				
				try{//运维监控
					rows=prodOrderDao.insertObject(entity);
				}catch(Throwable t){
					t.printStackTrace();
					//报警....
					throw new ServiceException("系统故障，正在恢复");
				}
				//验证结果
				if(rows==0)
					throw new ServiceException("save error");
				//3.返回结果
				//System.out.println("getId"+entity.getId());
				int deleteObjects = prodCartDao.deleteObjects(id[i]);
				if(deleteObjects==0)
					throw new ServiceException("记录可能已经不存在");
					}
				return rows;
	}

	//添加物品
	@Override
	public int insertWuPin(ProdCart entity) {
		if(entity==null)
			throw new IllegalArgumentException("对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("商品名称不能为空");
		if(StringUtils.isEmpty(entity.getPrice()))
		throw new IllegalArgumentException("商品单价不能为空");
		int fby = prodCartDao.findByUserId(entity);
		int rows=0;
		if(fby==0){
			entity.setNumber(1);
			//2.将数据更新到数据库
			rows=prodCartDao.insertWuPin(entity);
			if(rows==0)
				throw new ServiceException("此记录可能已经不存在");
		}else{
			rows=prodCartDao.updateNumber(entity);
			if(rows==0)
				throw new ServiceException("此记录可能已经不存在");
		}
			
		//3.对结果进行验证
		//4.返回结果(会返回给谁?调用者)
		return rows;	
	}

	@Override
	public int deletWuPin(ProdCart entity) {
		if(entity==null)
			throw new IllegalArgumentException("对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("商品名称不能为空");
		if(StringUtils.isEmpty(entity.getPrice()))
		throw new IllegalArgumentException("商品单价不能为空");
		int dby = prodCartDao.deletByUserId(entity);
		int rows=1;
		if(dby==0){
			throw new IllegalArgumentException("已经没有物品了，求你别再秀了");
		}
		else if(dby==1){
			prodCartDao.deleteWuPin(entity);
			if(rows==0)
				throw new ServiceException("此记录可能已经不存在");
		}else{
			prodCartDao.updateDeNumber(entity);
			if(rows==0)
				throw new ServiceException("此记录可能已经不存在");
		}
		/*if(fby==0){
			entity.setNumber(1);
			//2.将数据更新到数据库
			rows=prodCartDao.insertWuPin(entity);
			if(rows==0)
				throw new ServiceException("此记录可能已经不存在");
		}else{
			rows=prodCartDao.updateNumber(entity);
			if(rows==0)
				throw new ServiceException("此记录可能已经不存在");
		}*/
			
		//3.对结果进行验证
		//4.返回结果(会返回给谁?调用者)
		return rows;	
	}

}
