package com.june.bo.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.june.bo.IOrdersBo;
import com.june.dao.IOrdersDao;

@Transactional
@Service
public class OrdersBoImpl implements IOrdersBo {
	
	@Autowired
	private IOrdersDao dao;

	@Override
	public void saveOrderAndDetails(Map<String, Object> order,
			List<Map<String, Object>> details) {
		String uuid = dao.saveOrder(order);
		dao.saveOrderMenus(uuid,details);
	}

	@Override
	public List<Map<String, Object>> queryOrders() {
		return dao.queryOrders();
	}

	@Override
	public List<Map<String, Object>> queryOrderDetails(String oid) {
		return dao.queryOrderDetails(oid);
	}

}
