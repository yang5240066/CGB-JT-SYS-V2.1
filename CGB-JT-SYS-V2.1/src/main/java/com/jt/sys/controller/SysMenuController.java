package com.jt.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.sys.entity.SysMenu;
import com.jt.sys.service.SysMenuService;

@Controller
@RequestMapping("/menu/")
public class SysMenuController {

	@Autowired
	private SysMenuService sysMenuService;

	@RequestMapping("doMenuListUI")
	public String doMenuListUI() {
		return "sys/menu_list";
	}
	/**
	 * 通过此方法返回一个编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("doMenuEditUI")
	public String doMenuEditUI() {
		return "sys/menu_edit";
	}

	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects() {
		return new JsonResult(sysMenuService.findObjects());
	}

	/**
	 * rest风格的Url
	 * 菜单的删除涉及到角色表
	 * @Pathvariable 修饰的变量用于获取url中的参数值
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "doDeleteObject/{id}")
	@ResponseBody
	public JsonResult doDeleteObject(@PathVariable Integer id) {
		sysMenuService.deleteObject(id);
		return new JsonResult("delete OK");
	}

	/**
	 * 此方法用来呈现菜单列表中的节点树
	 * @return
	 */
	@RequestMapping("doFindZtreeMenuNodes")
	@ResponseBody
	public JsonResult doFindZtreeMenuNodes() {
		return new JsonResult(sysMenuService.findZtreeMenuNodes());
	}

	/**
	 * 菜单的新增和修改都没有涉及到角色表
	 * @param entity
	 * @return
	 */
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysMenu entity) {
		//System.out.println(entity);
		sysMenuService.saveObject(entity);
		return new JsonResult("保存OK");
	}
	
	 @RequestMapping("doUpdateObject")
	  @ResponseBody//update多一个id
	  public JsonResult doUpdateObject(SysMenu entity){
		  sysMenuService.updateObject(entity);
		  return new JsonResult("update ok");
	  }

}
