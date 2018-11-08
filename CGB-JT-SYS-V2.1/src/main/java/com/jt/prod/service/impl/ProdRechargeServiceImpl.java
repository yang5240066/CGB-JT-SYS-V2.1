package com.jt.prod.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.jt.common.exception.ServiceException;
import com.jt.common.vo.PageObject;
import com.jt.prod.dao.ProdRechargeDao;
import com.jt.prod.dao.ProdWalletDao;
import com.jt.prod.entity.ProdMenu;
import com.jt.prod.entity.ProdRecharge;
import com.jt.prod.entity.ProdWallet;
import com.jt.prod.service.ProdRechargeService;

@Service
public class ProdRechargeServiceImpl implements ProdRechargeService {
	@Autowired
	ProdRechargeDao prodRechargeDao;
	
	@Autowired
	private ProdWalletDao prodWalletDao;

	@Override
	public PageObject<ProdRecharge> findObjectsByUserId(Integer userId,Integer pageCurrent) {
		//1.参数有效性验证(pageCurrent)
				if(pageCurrent==null||pageCurrent<1)//顺序不能变
					throw new IllegalArgumentException("当前页码值无效");

				//2.基于name参数查询总记录数
				int rowCount = 0;
				try {
					rowCount = prodRechargeDao.getRowCount(userId);
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
				int pageSize = 6;
				//4.2计算当前页记录的起始位置
				//int startIndex = (pageCurrent-1)*pageSize;
				PageHelper.startPage(pageCurrent, pageSize);
				//4.3从当前页起始位置开始查询当前页记录
				List<ProdRecharge> records = prodRechargeDao.findObjectsByUserId(userId);

				//5.对查询的结果进行封装
				PageObject<ProdRecharge> pageObject = new PageObject<>();//jdk1.7以后才允许
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
				return pageObject;
	}

	@Override
	public ProdRecharge findProdRechargeByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateProdRecharge(ProdRecharge prodRecharge) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertProdRecharge(Integer userId, Double rechargeAmount) {
		if (rechargeAmount == null || rechargeAmount < 0)
			throw new IllegalArgumentException("充值金额不能为空且大于0");
		int rows = 0;
		// 2.将数据写入到数据库
		try {// 运维监控
			ProdRecharge pr=new ProdRecharge();
			pr.setUserId(userId);
			ProdWallet pw = prodWalletDao.findProdWalletByUserId(userId);
			Double beforeBalance = pw.getBalance();
			pr.setBeforeBalance(beforeBalance);
			pr.setRechargeAmount(rechargeAmount);
			Double afterBalance = beforeBalance+rechargeAmount;
			pr.setAfterBalance(afterBalance);
			Date rechargeTime = new Date();
			pr.setRechargeTime(rechargeTime);
			pw.setBalance(afterBalance);
			prodWalletDao.updateProdWalletBalance(pw);
			rows=prodRechargeDao.insertProdRecharge(pr);
		} catch (Throwable t) {
			t.printStackTrace();
			// 报警....
			throw new ServiceException("系统故障，正在恢复");
		}
		// 验证结果
		if (rows == 0)
			throw new ServiceException("save error");
		// 3.返回结果
		return rows;

	}

	@Override
	public int deleteProdRecharge(Integer userId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
