package com.june.vueService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.june.bo.IHMenuBo;

//@CrossOrigin(origins = "http://192.168.1.137:8011")//为类设置跨域
@RestController
@RequestMapping("menu")
public class HMenuController {
	
	@Autowired
	private IHMenuBo menuBo;
	
	@RequestMapping("getmenus")
	@ResponseBody
	public List<List<Map<String, Object>>> getMenus() {
		return menuBo.getMenus3();
	}
}
