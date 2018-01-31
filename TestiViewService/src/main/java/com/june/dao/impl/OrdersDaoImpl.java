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

	@Override
	public List<Map<String, Object>> queryOrders() {
		String sql ="select * from food_orders o WHERE o.enable=1 ORDER BY o.createdtime DESC";
		return DataBaseUtils.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> queryOrderDetails(String oid) {
		String sql="select om.*,dt.tname from food_ordermenus om join food_hmenu m on om.mid=m.id join food_dishestypes dt on dt.tid = m.type WHERE om.oid=?";
		return DataBaseUtils.queryForList(sql,oid);
	}

	@Override
	public void delOrder(String oid) {
		String sql="update food_orders set enable=0 WHERE oid=?";
		DataBaseUtils.update(sql, oid);
		//sql = "DELETE from food_ordermenus WHERE oid=?";
		//DataBaseUtils.update(sql, oid);
	}

}
