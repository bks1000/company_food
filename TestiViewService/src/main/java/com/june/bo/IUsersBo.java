package com.june.bo;

import java.util.List;
import java.util.Map;

public interface IUsersBo {

	/**
	 * 用户登录获取用户信息，(存入缓存)
	 * @param username
	 * @param pwd
	 * @return
	 */
	public Map<String, Object> userLogin(String username,String pwd);
}
