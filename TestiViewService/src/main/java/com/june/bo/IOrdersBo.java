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

}
