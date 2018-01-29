package com.june.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器，在config中配置好，就可以用的。这个拦截器是跨域的配置，用不着了。
 * @author lenovo
 *
 */
public class MyInterceptor implements HandlerInterceptor {

	/**  
     *   
     * 在DispatcherServlet完全处理完请求后被调用  
     * 当有拦截器抛出异常时,  
     * 会从当前拦截器往回执行所有的拦截器的afterCompletion()  
     *   
     * @param request  
     *   
     * @param response  
     *   
     * @param handler  
     *   
     */  
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO 自动生成的方法存根

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO 自动生成的方法存根

	}

	/**  
     *   
     * 在业务处理器处理请求之前被调用 如果返回false   
     * 从当前的拦截器往回执行所有拦截器的afterCompletion(),  
     * 再退出拦截器链, 如果返回true 执行下一个拦截器,  
     * 直到所有的拦截器都执行完毕 再执行被拦截的Controller  
     * 然后进入拦截器链,  
     * 从最后一个拦截器往回执行所有的postHandle()  
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()  
     *   
     * @param  request  
     *   
     * @param  response  
     */  
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse response,
			Object arg2) throws Exception {
		System.out.println("555");
		response.setHeader("Access-Control-Allow-Origin", "*");  
        response.setHeader("Access-Control-Allow-Methods", "*");  
        response.setHeader("Access-Control-Max-Age", "3600");  
        response.setHeader("Access-Control-Allow-Headers",  
                "Origin, X-Requested-With, Content-Type, Accept");  
        return true;  
	}

}
