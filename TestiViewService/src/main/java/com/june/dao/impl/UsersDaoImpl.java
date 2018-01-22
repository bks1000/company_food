package com.june.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.june.dao.IUsersDao;
import com.june.utils.DataBaseUtils;

@Repository
public class UsersDaoImpl implements IUsersDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*public UsersDaoImpl(JdbcTemplate tmp) {
		this.jdbcTemplate = tmp;
	}*/

	@Override
	public Map<String, Object> userLogin(String username, String pwd) {
		String sql = "select * from food_users u WHERE u.username=? and u.pwd=?";
		return DataBaseUtils.queryForMap(sql,username,pwd);
		//return jdbcTemplate.queryForMap(sql,username,pwd);
	}

}
