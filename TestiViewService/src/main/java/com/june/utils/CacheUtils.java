package com.june.utils;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CacheUtils {
	@Value("${cache.timeout}")
	public int Timeout = 0;
	public static String PT_APPID = "0";
	@Value("${cache.type}")
	private static int CacheType = 2;
	/*static {
		Properties prop = ManageUtils.getApplicationSettings();
		if (prop != null) {
			Timeout = Integer.parseInt(prop.getProperty("cache.timeout", "0"));// key超时时间（秒）
			CacheType = Integer.parseInt(prop.getProperty("cache.type", "0"));
		}
	}*/

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
		if (CacheType == 0) {
			//return DbCacheUtils.get(key, appId);
		} else if (CacheType == 1) {
			//return JedisUtils.get(key, appId);
		} else if (CacheType == 2) {
			return SessionUtils.get(key, appId);
		}
		return null;
	}

	/**
	 * 设置key对应的字符串值(平台)
	 * 
	 * @param key
	 * @param value
	 */
	public static void set(final String key, final String value) {
		set(key, value, PT_APPID);
	}

	/**
	 * 设置key对应的字符串值
	 * 
	 * @param key
	 * @param value
	 * @param appId
	 */
	public static void set(final String key, final String value, final String appId) {
		if (CacheType == 0) {
			//DbCacheUtils.set(key, value, appId);
		} else if (CacheType == 1) {
			//JedisUtils.set(key, value, appId);
		} else if (CacheType == 2) {
			SessionUtils.set(key, value, appId);
		}
	}

	/**
	 * 设置key的过期时间(平台)
	 * 
	 * @param key
	 * @param seconds
	 */
	public static void expire(final String key, final int seconds) {
		expire(key, seconds, PT_APPID);
	}

	/**
	 * 设置key的过期时间
	 * 
	 * @param key
	 * @param seconds
	 * @param appId
	 */
	public static void expire(final String key, final int seconds, final String appId) {
		if (CacheType == 0) {
			//DbCacheUtils.expire(key, seconds, appId);
		} else if (CacheType == 1) {
			//JedisUtils.expire(key, seconds, appId);
		} else if (CacheType == 2) {
			SessionUtils.expire(key, seconds, appId);
		}
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
		if (CacheType == 0) {
			//return DbCacheUtils.exists(key, appId);
		} else if (CacheType == 1) {
			//return JedisUtils.exists(key, appId);
		} else if (CacheType == 2) {
			return SessionUtils.exists(key, appId);
		}
		return false;
	}

	/**
	 * 设置单个hash字段值(平台)
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public void hset(final String key, String field, String value) {
		hset(key, field, value, PT_APPID);
	}

	/**
	 * 设置单个hash字段值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @param appId
	 */
	public void hset(final String key, String field, String value, final String appId) {
		if (CacheType == 0) {
			//DbCacheUtils.hset(key, field, value, appId);
		} else if (CacheType == 1) {
			//JedisUtils.hset(key, field, value, appId);
		} else if (CacheType == 2) {
			SessionUtils.hset(key, field, value, appId);
		}
		if (Timeout > 0) {
			expire(key, Timeout, appId);
		}
	}

	/**
	 * 设置多个hash字段值(平台)
	 * 
	 * @param key
	 * @param hash
	 * @return
	 */
	public boolean hmset(final String key, final Map<String, String> hash) {
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
	public boolean hmset(final String key, final Map<String, String> hash, final String appId) {
		boolean res = false;
		if (CacheType == 0) {
			//res = DbCacheUtils.hmset(key, hash, appId);
		} else if (CacheType == 1) {
			//res = JedisUtils.hmset(key, hash, appId);
		} else if (CacheType == 2) {
			res = SessionUtils.hmset(key, hash, appId);
		}
		if (Timeout > 0) {
			expire(key, Timeout, appId);
		}
		return res;
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
		if (CacheType == 0) {
			//return DbCacheUtils.hget(key, field, appId);
		} else if (CacheType == 1) {
			//return JedisUtils.hget(key, field, appId);
		} else if (CacheType == 2) {
			return SessionUtils.hget(key, field, appId);
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
	public static Map<String, String> hgetAll(final String key, final String appId) {
		if (CacheType == 0) {
			//return DbCacheUtils.hgetAll(key, appId);
		} else if (CacheType == 1) {
			//return JedisUtils.hgetAll(key, appId);
		} else if (CacheType == 2) {
			return SessionUtils.hgetAll(key, appId);
		}
		return null;
	}

	/**
	 * 删除key(平台)
	 * 
	 * @param key
	 */
	public static void del(final String key) {
		del(key, PT_APPID);
	}

	/**
	 * 删除key
	 * 
	 * @param key
	 * @param appId
	 */
	public static void del(final String key, final String appId) {
		if (CacheType == 0) {
			//DbCacheUtils.del(key, appId);
		} else if (CacheType == 1) {
			//JedisUtils.del(key, appId);
		} else if (CacheType == 2) {
			SessionUtils.del(key, appId);
		}
	}

	/**
	 * 删除key(所有)
	 * 
	 * @param key
	 */
	public static void delAll(final String key) {
		if (CacheType == 0) {
			//DbCacheUtils.delAll(key);
		} else if (CacheType == 1) {
			//JedisUtils.delAll(key);
		} else if (CacheType == 2) {
			SessionUtils.delAll(key);
		}
	}
}
