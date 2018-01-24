package com.june.vueService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.june.bo.IUsersBo;

//@CrossOrigin(origins = "http://192.168.1.137:8011")//为类设置跨域
@RestController
public class PersonController extends BaseController {
    
	@Autowired
    private IUsersBo userbo;
    
	//@CrossOrigin(origins = "http://localhost:3000")//为方法设置跨域
    @RequestMapping(value = "/login",consumes = "application/json")
    @ResponseBody
    public String login(HttpServletRequest request,HttpServletResponse response,@RequestBody Map<String, Object> paramsMap) {
    	try {
			Map<String, Object> userInfo = null;
			String token = null;

			//String username = request.getParameter("username");
			//String pwd = request.getParameter("pwd");
			String username = paramsMap.get("username").toString();
			String pwd = paramsMap.get("pwd").toString();
			
			userInfo = userbo.userLogin(username,pwd);

			// 保存用户信息并返回token
			if (userInfo != null && userInfo.size() > 0) {
				
				token = tokenUtils.createJWT(username, userInfo.get("id").toString());
				
				Map<String, String> hash = new HashMap<String, String>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				hash.put("LOGIN_TIME", sdf.format(new Date()));
				for (Entry<String, Object> entry : userInfo.entrySet()) {
					String key = entry.getKey();
					Object val = entry.getValue();
					if (key != null && val != null) {
						hash.put(key, val.toString());
					}
				}
				cacheUtils.hmset(token, hash);
				
				return token;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login error";
	}
}

