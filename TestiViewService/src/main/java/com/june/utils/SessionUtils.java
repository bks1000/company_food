package com.june.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionUtils {
	private static String PT_APPID = CacheUtils.PT_APPID;

	/**
	 * 获取Session对象
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		try {
			ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (sra != null) {
				HttpServletRequest request = sra.getRequest();
				if (request != null) {
					return request.getSession();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成Key
	 * 
	 * @param key
	 * @param appId
	 * @return
	 */
	private static String buildAppKey(final String key, final String appId) {
		if (appId == null || appId.length() == 0)
			return key;
		String newKey = key + "." + appId;
		return newKey;
	}

	/**
	 * 获取key对应的字符串值(平台)
	 * 
	 * @param key
	 * @return
	 */
	public static String get(final String key) {
		return get(key, PT_APPID);
	}

	/**
	 * 获取key对应的字符串值
	 * 
	 * @param key
	 * @param appId
	 * @return
	 */
	public static String get(final String key, final String appId) {
		HttpSession session = getSession();
		if (session == null)
			return null;
		String newKey = buildAppKey(key, appId);
		return (String) session.getAttribute(newKey);
	}

	/**
	 * 设置key对应的字符串值(平台)
	 * 
	 * @param key
	 * @param value
	 */
	public static boolean set(final String key, final String value) {
		return set(key, value, PT_APPID);
	}

	/**
	 * 设置key对应的字符串值
	 * 
	 * @param key
	 * @param value
	 * @param appId
	 */
	public static boolean set(final String key, final String value, final String appId) {
		HttpSession session = getSession();
		if (session == null)
			return false;
		String newKey = buildAppKey(key, appId);
		session.setAttribute(newKey, value);
		return true;
	}

	/**
	 * 设置key的过期时间(平台)
	 * 
	 * @param key
	 * @param seconds
	 */
	public static boolean expire(final String key, final int seconds) {
		return expire(key, seconds, PT_APPID);
	}

	/**
	 * 设置key的过期时间
	 * 
	 * @param key
	 * @param seconds
	 * @param appId
	 */
	public static boolean expire(final String key, final int seconds, final String appId) {
		HttpSession session = getSession();
		if (session == null)
			return false;
		session.setMaxInactiveInterval(seconds);// 以秒为单位
		return true;
	}

	/**
	 * 判断key是否存在(平台)
	 * 
	 * @param key
	 * @return
	 */
	public static boolean exists(final String key) {
		return exists(key, PT_APPID);
	}

	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 * @param appId
	 * @return
	 */
	public static boolean exists(final String key, final String appId) {
		HttpSession session = getSession();
		if (session == null)
			return false;
		String newKey = buildAppKey(key, appId);
		Object obj = session.getAttribute(newKey);
		return obj != null;
	}

	/**
	 * 设置单个hash字段值(平台)
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public static boolean hset(final String key, String field, String value) {
		return hset(key, field, value, PT_APPID);
	}

	/**
	 * 设置单个hash字段值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @param appId
	 */
	public static boolean hset(final String key, String field, String value, final String appId) {
		HttpSession session = getSession();
		if (session == null)
			return false;
		Map<String, String> map = hgetAll(key, appId);
		if (map == null) {
			map = new HashMap<String, String>();
		}
		map.put(field, value);
		return hmset(key, map, appId);
	}

	/**
	 * 设置多个hash字段值
	 * 
	 * @param key
	 * @param hash
	 * @return
	 */
	public static boolean hmset(final String key, final Map<String, String> hash) {
		return hmset(key, hash, PT_APPID);
	}

	/**
	 * 设置多个hash字段值
	 * 
	 * @param key
	 * @param hash
	 * @param appId
	 * @return
	 */
	public static boolean hmset(final String key, final Map<String, String> hash, final String appId) {
		HttpSession session = getSession();
		if (session == null)
			return false;
		String newKey = buildAppKey(key, appId);
		session.setAttribute(newKey, hash);
		return true;
	}

	/**
	 * 返回 key 指定的哈希集中该字段所关联的值(平台)
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static String hget(final String key, final String field) {
		return hget(key, field, PT_APPID);
	}

	/**
	 * 返回 key 指定的哈希集中该字段所关联的值
	 * 
	 * @param key
	 * @param field
	 * @param appId
	 * @return
	 */
	public static String hget(final String key, final String field, final String appId) {
		Map<String, String> map = hgetAll(key, appId);
		if (map != null && map.containsKey(field)) {
			return map.get(field);
		}
		return null;
	}

	/**
	 * 返回 key 指定的哈希集中所有的字段和值(平台)
	 * 
	 * @param key
	 * @return
	 */
	public static Map<String, String> hgetAll(final String key) {
		return hgetAll(key, PT_APPID);
	}

	/**
	 * 返回 key 指定的哈希集中所有的字段和值
	 * 
	 * @param key
	 * @param appId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> hgetAll(final String key, final String appId) {
		HttpSession session = getSession();
		if (session == null)
			return null;
		String newKey = buildAppKey(key, appId);
		return (Map<String, String>) session.getAttribute(newKey);
	}

	/**
	 * 删除key(平台)
	 * 
	 * @param key
	 */
	public static boolean del(final String key) {
		return del(key, PT_APPID);
	}

	/**
	 * 删除key
	 * 
	 * @param key
	 * @param appId
	 */
	public static boolean del(final String key, final String appId) {
		HttpSession session = getSession();
		if (session == null)
			return false;
		String newKey = buildAppKey(key, appId);
		session.removeAttribute(newKey);
		return true;
	}

	/**
	 * 删除key(所有)
	 * 
	 * @param key
	 */
	public static boolean delAll(final String key) {
		HttpSession session = getSession();
		if (session == null)
			return false;
		session.invalidate();
		return true;
	}
}
