package com.june.bo;

import java.util.List;
import java.util.Map;

/**
 * 菜单类
 * 主要是查询菜单（暂不提供菜品类型和菜单的维护功能。在数据中维护）
 * @author lenovo
 *
 */
public interface IHMenuBo {
	
	/**
	 * 获取菜单数据（树形结构，可供树控件或级联控件使用）
	 * @return
	 */
	public List<Map<String,Object>> getMenus();
	
	/**
	 * 获取菜单数据，使用table列表的形式展现，勾选，提交。可以在弹出框中查看当前所订的菜单条目。
	 * @return
	 */
	public List<Map<String, Object>> getMenus2();
	
	/**
	 * 获取菜单数据，使用table列表的形式展现，勾选，提交。可以在弹出框中查看当前所订的菜单条目。
	 * @return
	 */
	public List<List<Map<String, Object>>> getMenus3();

}
