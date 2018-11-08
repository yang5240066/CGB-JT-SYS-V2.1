package com.jt.prod.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.jt.common.exception.ServiceException;
import com.jt.common.vo.PageObject;
import com.jt.prod.dao.ProdMenuDao;
import com.jt.prod.entity.ProdCart;
import com.jt.prod.entity.ProdMenu;
import com.jt.prod.service.ProdMenuService;

@Service
public class ProdMenuServiceImpl implements ProdMenuService{

	
	
	//IOC
	@Autowired//根据属性类型,必须要自动注入,给dao赋值
	private ProdMenuDao productDao;
	
	@Override
	public ProdMenu getObjectById(Integer id) {
		return productDao.getObjectById(id);
	}
	
	@Override
	public PageObject<ProdMenu> findPageObjects
	(String name, Integer pageCurrent) {
		//1.参数有效性验证(pageCurrent)
		if(pageCurrent==null||pageCurrent<1)//顺序不能变
			throw new IllegalArgumentException("当前页码值无效");

		//2.基于name参数查询总记录数
		int rowCount = 0;
		try {
			rowCount = productDao.getRowCount(name);
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
		List<ProdMenu> records = productDao.findPageObjects(name/*, startIndex, pageSize*/);

		//5.对查询的结果进行封装
		PageObject<ProdMenu> pageObject = new PageObject<>();//jdk1.7以后才允许
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
	public int deleteObjects(Integer... ids) {
		//1.判定参数合法性,StringUtils是Spring的
//		if(StringUtils.isEmpty(ids))
		if(ids==null||ids.length==0)
			throw new IllegalArgumentException("请选择一个");
		//2.执行删除操作
		int rows = -1;
		try{//数据库是专门的服务器,tomcat也是一台专门的服务器
			rows=productDao.deleteObjects(ids);
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
	public int saveObject(ProdMenu entity) {
		//1.参数合法验证
		if(entity==null)
			throw new IllegalArgumentException("保存商品不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("商品名称不能为空");
		if(StringUtils.isEmpty(entity.getPrice()))
			throw new IllegalArgumentException("商品单价不能为空");
		//2.将数据写入到数据库
		int rows;
		try{//运维监控
			rows=productDao.insertObject(entity);
		}catch(Throwable t){
			t.printStackTrace();
			//报警....
			throw new ServiceException("系统故障，正在恢复");
		}
		//验证结果
		if(rows==0)
			throw new ServiceException("save error");
		//3.返回结果
		return rows;

	}

	@Override
	public int updateObject(ProdMenu entity) {
		//1.合法性验证
		  if(entity==null)
		  throw new IllegalArgumentException("对象不能为空");
		  if(StringUtils.isEmpty(entity.getName()))
		  throw new IllegalArgumentException("商品名称不能为空");
		  if(StringUtils.isEmpty(entity.getPrice()))
		  throw new IllegalArgumentException("商品单价不能为空");
		  //2.将数据更新到数据库
		  int rows=productDao.updateObject(entity);
		  //3.对结果进行验证
		  if(rows==0)
		  throw new ServiceException("此记录可能已经不存在");
		  //4.返回结果(会返回给谁?调用者)
		  return rows;	  
	}

	@Override
	public List<ProdMenu> findAllPageObjects() {
		List<ProdMenu> list = productDao.findPageObjects("");
		return list;
	}

	
	@Override
	public int insertWuPin(ProdCart entity) {
		
		if(entity==null)
			throw new IllegalArgumentException("对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("商品名称不能为空");
		if(StringUtils.isEmpty(entity.getPrice()))
		throw new IllegalArgumentException("商品单价不能为空");
		int fby = productDao.findByUserId(entity);
		int rows=0;
		if(fby==0){
			entity.setNumber(1);
			//2.将数据更新到数据库
			rows=productDao.insertWuPin(entity);
			if(rows==0)
				throw new ServiceException("此记录可能已经不存在");
		}else{
			rows=productDao.updateNumber(entity);
			if(rows==0)
				throw new ServiceException("此记录可能已经不存在");
		}
			
		//3.对结果进行验证
		//4.返回结果(会返回给谁?调用者)
		return rows;	
	}
	
	
}
