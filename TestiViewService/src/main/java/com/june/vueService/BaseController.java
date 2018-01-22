package com.june.vueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.june.utils.CacheUtils;
import com.june.utils.TokenUtils;

@Controller
public class BaseController {
	
	@Autowired
	public TokenUtils tokenUtils;
	
	@Autowired
	public CacheUtils cacheUtils;
}
