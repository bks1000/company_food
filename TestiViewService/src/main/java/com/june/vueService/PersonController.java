package com.june.vueService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.june.bo.IUsersBo;

@RestController
public class PersonController extends BaseController {
    
	@Autowired
    private IUsersBo userbo;
    
    @RequestMapping("/login")
    @ResponseBody
    public String login(HttpServletRequest request,HttpServletResponse response) {
    	try {
			Map<String, Object> userInfo = null;
			String token = null;
			//Map<String, Object> params = PageUtils.getParameters(request);
			//String username = params.get("username").toString();
			//String pwd = params.get("pwd").toString();
			String username = request.getParameter("username");
			String pwd = request.getParameter("pwd");
			
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

