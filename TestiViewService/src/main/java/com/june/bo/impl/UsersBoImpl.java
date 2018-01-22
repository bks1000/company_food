package com.june.bo.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.june.bo.IUsersBo;
import com.june.dao.IUsersDao;


@Component
public class UsersBoImpl implements IUsersBo {
	
	@Autowired
	private IUsersDao userDao;
	
	/*public UsersBoImpl(IUsersDao dao) {
		this.userDao = dao;
	}*/

	@Override
	public Map<String, Object> userLogin(String username, String pwd) {
		return userDao.userLogin(username, pwd);
	}

}
