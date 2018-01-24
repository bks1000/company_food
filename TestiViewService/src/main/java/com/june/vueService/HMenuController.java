package com.june.vueService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.june.bo.IHMenuBo;

@RestController
@RequestMapping("menu")
public class HMenuController {
	
	@Autowired
	private IHMenuBo menuBo;
	
	@RequestMapping("getmenus")
	@ResponseBody
	public List<Map<String, Object>> getMenus() {
		return menuBo.getMenus2();
	}
	
}
