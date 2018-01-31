package com.june.bo;

import java.util.List;
import java.util.Map;

public interface IOrdersBo {
	
	/**
	 * 保存订单主表
	 * 保存订单菜单表
	 * @param order
	 * @param details
	 */
	public void saveOrderAndDetails(Map<String, Object> order,List<Map<String, Object>> details);

	/**
	 * 查询订单主表
	 * @return
	 */
	public List<Map<String, Object>> queryOrders();
	
	/**
	 * 查询订单明细
	 * @param oid 订单主表ID
	 * @return
	 */
	public List<Map<String, Object>> queryOrderDetails(String oid);
	
	/**
	 * 删除订单
	 * @param oid
	 */
	public void delOrder(String oid);
}
