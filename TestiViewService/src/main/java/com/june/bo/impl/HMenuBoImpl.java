package com.june.bo.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.june.bo.IHMenuBo;
import com.june.dao.IHMenuDao;

@Service
public class HMenuBoImpl implements IHMenuBo {
	
	@Autowired
	private IHMenuDao dao;

	@Override
	public List<Map<String, Object>> getMenus() {
		return dao.getMenus();
	}

	@Override
	public List<Map<String, Object>> getMenus2() {
		return dao.getMenus2();
	}

}
