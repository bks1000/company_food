package com.june.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.june.dao.IOrdersDao;
import com.june.utils.DataBaseUtils;

@Repository
public class OrdersDaoImpl implements IOrdersDao{

	@Override
	public String saveOrder(Map<String, Object> params) {
		String sql = "select uuid() as uuid";
		String uuid = DataBaseUtils.queryForScalar(sql, String.class);
		sql = "insert into food_orders VALUES(?,0,?,?,now(),1)";
		DataBaseUtils.update(sql,uuid,params.get("total"),params.get("userid"));
		return uuid;
	}

	@Override
	public void saveOrderMenus(String oid,List<Map<String, Object>> lst) {
		String sql= "insert into food_ordermenus(id,oid,mid,mname,nums,price) VALUES(uuid(),?,?,?,?,?)";
		for (Map<String, Object> map : lst) {
			DataBaseUtils.update(sql, oid,map.get("id").toString(),map.get("name").toString(),
					map.get("count").toString(),map.get("price").toString());
		}
	}

}
