package com.june.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.june.dto.PageDTO;

@Component
public class PageUtils {
	
	@Autowired
	private TokenUtils tokenUtils;

	/**
	 * @param request
	 *            请求中的request
	 * @return 返回Map获取request中的参数
	 */
	public static Map<String, Object> getParameters(HttpServletRequest request) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Enumeration<String> names = request.getParameterNames();

		while (names.hasMoreElements()) {
			String key = names.nextElement();
			String value = request.getParameter(key);
			map.put(key, value);
		}
		/*if (!map.containsKey("token") || map.get("token") == null) {
			String token = TokenUtils.getToken(request);
			map.put("token", token);
		}*/
		return map;
	}

	/**
	 * 获取消息头
	 */
	public static Map<String, String> getHeadersInfo(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 获取Cache中的用户信息，用于日志管理。
	 */
	public static Map<String, Object> getCacheInfoForLog(String token) {
		Map<String, Object> map = new HashMap<String, Object>();
		String orgID = CacheUtils.hget(token, "org_id");
		String orgName = CacheUtils.hget(token, "org_name");
		String logName = CacheUtils.hget(token, "logon_name");
		String userID = CacheUtils.hget(token, "guid");
		String userName = CacheUtils.hget(token, "username");
		map.put("orgID", orgID);
		map.put("orgName", orgName);
		map.put("logName", logName);
		map.put("userID", userID);
		map.put("userName", userName);
		return map;
	}

	/**
	 * list分页效果
	 * 
	 * @param paramsList
	 * @param currentpage
	 *            第几页 默认从1开始
	 * @param pagesize
	 *            每页显示多少条 默认显示20条
	 * @return
	 */
	public static List<Map<String, Object>> pageForList(List<Map<String, Object>> paramsList, String currentpage,
			String pagesize) {

		int pageSize = pagesize == null || pagesize.equals("") ? 20 : Integer.parseInt(pagesize);
		int currentPage = currentpage == null || currentpage.equals("") ? 1 : Integer.parseInt(currentpage);
		if(Math.ceil(paramsList.size()/Double.valueOf(pageSize))<currentPage){
			currentPage= 1;
		}
		int startIndex = (currentPage - 1) * pageSize;// 起始
		int endIndex = currentPage * pageSize;// 终止
		// 判断终止数是否大于总条数，若大于则将总条数赋值给终止数
		if (endIndex > paramsList.size())
			endIndex = paramsList.size();

		paramsList = paramsList.subList(startIndex, endIndex);
		return paramsList;

	}

	/**
	 * list分页效果
	 * 
	 * @param paramsList
	 * @param currentpage
	 *            第几页 默认从1开始
	 * @param pagesize
	 *            每页显示多少条 默认显示20条
	 * @return
	 */
	public static PageDTO listForPage(List<Map<String, Object>> paramsList, String currentpage, String pagesize) {
		PageDTO page = new PageDTO();

		int pageSize = pagesize == null || pagesize.equals("") ? 20 : Integer.parseInt(pagesize);
		int currentPage = currentpage == null || currentpage.equals("") ? 1 : Integer.parseInt(currentpage);
		if(Math.ceil(paramsList.size()/Double.valueOf(pageSize))<currentPage){
			currentPage= 1;
		}
		int startIndex = (currentPage - 1) * pageSize;// 起始
		int endIndex = currentPage * pageSize;// 终止
		// 判断终止数是否大于总条数，若大于则将总条数赋值给终止数
		if (endIndex > paramsList.size())
			endIndex = paramsList.size();
		page.setTotal(paramsList.size());
		paramsList = paramsList.subList(startIndex, endIndex);
		page.setData(paramsList);
		return page;

	}
	/**
	 * 读取缓存中的内容
	 * @param req
	 * @param key
	 * @return
	 */
	public String getCacheData(String token,String key){
        //String token= tokenUtils.getToken(req);
        String value= CacheUtils.hget(token, key);//平台用户id
		return value;

    }
}
