package com.june.dao;

import java.util.List;
import java.util.Map;


public interface IHMenuDao {
	
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

}
