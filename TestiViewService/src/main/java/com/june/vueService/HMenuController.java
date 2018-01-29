package com.june.vueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.june.bo.IHMenuBo;
import com.june.bo.IOrdersBo;
import com.june.dto.Element;

//@CrossOrigin(origins = "http://192.168.1.137:8011")//为类设置跨域
@RestController
@RequestMapping("menu")
public class HMenuController extends BaseController {
	
	@Autowired
	private IHMenuBo menuBo;
	
	@Autowired
	private IOrdersBo orderBo;
	
	/**
	 * 获取菜单
	 * @return
	 */
	@RequestMapping("getmenus")
	@ResponseBody
	public List<List<Map<String, Object>>> getMenus() {
		return menuBo.getMenus3();
	}
	
	/**
	 * 保存订单
	 * @param request
	 * @param orders
	 * @return
	 */
	@RequestMapping( value="saveOrder",consumes="application/json")
	@ResponseBody
	public Map<String, Object> saveOrders(HttpServletRequest request,@RequestBody Map<String, Object> data) {
		Map<String, Object> result = new HashMap<String, Object>();
		String token = data.get("token").toString();
		//TODO:验证token的逻辑
		List<Map<String, Object>> orders = (List<Map<String,Object>>)data.get("orders");
	    String userid = this.pageUtils.getCacheData(token, Element.Cache.ID);
	    List<Map<String, Object>> o1 = orders.subList(0, orders.size()-1);//点菜明细
	    //System.out.println(o1.toString());
	    List<Map<String, Object>> o2 = orders.subList(orders.size()-1, orders.size());//订单总计
		//System.out.println(o2);
	    
	    o2.get(0).put("userid", userid);
	    o2.get(0).put("total", o2.get(0).get("price"));
	    orderBo.saveOrderAndDetails(o2.get(0), o1);
		return result;
	}
}
