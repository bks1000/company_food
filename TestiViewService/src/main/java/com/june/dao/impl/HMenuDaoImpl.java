package com.june.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.june.dao.IHMenuDao;
import com.june.utils.DataBaseUtils;

@Repository
public class HMenuDaoImpl implements IHMenuDao {

	@Override
	public List<Map<String, Object>> getMenus() {
		String sql = "select tid,tname from food_dishestypes";
		List<Map<String, Object>> tps = DataBaseUtils.queryForList(sql);
		
		sql = "select m.id,m.name,m.price,m.type FROM food_hmenu m WHERE m.enable=1";
		List<Map<String, Object>> mus = DataBaseUtils.queryForList(sql);
		
		for (Map<String,Object> map : tps ){
            List<Map<String,Object>> children = new ArrayList<>();
            mus.forEach(s->{
                if(s.get("type")==null?false:s.get("type").equals(map.get("tid"))){
                    children.add(s);
                }
            });
            map.put("children", children);
		}
		return tps;
	}

	@Override
	public List<Map<String, Object>> getMenus2() {
		String sql = "select m.id,m.name,m.price,m.type,t.tname FROM food_hmenu m JOIN food_dishestypes t ON m.type=t.tid  WHERE m.enable=1 ORDER BY t.tid,m.id";
		return DataBaseUtils.queryForList(sql);
	}

	@Override
	public List<List<Map<String, Object>>> getMenus3() {
		List<List<Map<String, Object>>> result = new ArrayList<List<Map<String,Object>>>();
		String sql = "select tid,tname from food_dishestypes";
		List<Map<String, Object>> tps = DataBaseUtils.queryForList(sql);
		tps.forEach(t->{
			result.add(getHMenusByType((int)t.get("tid")));
		});
		return result;
	}
	
	private List<Map<String, Object>> getHMenusByType(int tid) {
		String sql = "select m.id,m.name,m.price,m.type,t.tname FROM food_hmenu m JOIN food_dishestypes t ON m.type=t.tid  WHERE m.enable=1 and t.tid=? ORDER BY t.tid,m.id";
		return DataBaseUtils.queryForList(sql,tid);
	}

}
