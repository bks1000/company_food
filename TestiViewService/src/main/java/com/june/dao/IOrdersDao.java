package com.june.dao;

import java.util.List;
import java.util.Map;

public interface IOrdersDao {

	/**
	 * 保存订单主表
	 * @param params
	 */
	public String saveOrder(Map<String, Object> params);
	
	/**
	 * 保存订单，菜单关系表
	 * @param oid 订单主表id
	 * @param lst
	 */
	public void saveOrderMenus(String oid,List<Map<String, Object>> lst);
}
