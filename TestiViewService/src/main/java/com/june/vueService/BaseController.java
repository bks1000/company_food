package com.june.vueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.june.utils.CacheUtils;
import com.june.utils.PageUtils;
import com.june.utils.TokenUtils;

/**
 * controller基类，包括token,cache工具
 * @author lenovo
 *
 */
@Controller
public class BaseController {
	
	@Autowired
	public TokenUtils tokenUtils;
	
	@Autowired
	public CacheUtils cacheUtils;
	
	@Autowired
	public PageUtils pageUtils;
}
