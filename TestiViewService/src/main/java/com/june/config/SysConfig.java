package com.june.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.june.Interceptor.MyInterceptor;

/**
 * 设置跨域
 * Spring Boot全局支持CORS（跨源请求）
 * @author lenovo
 *
 */
@Configuration
@EnableWebMvc
public class SysConfig extends WebMvcConfigurerAdapter  {
	
	/*@Override
    public void addInterceptors(InterceptorRegistry registry) {
		System.out.println("3");
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }*/
	
	/*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")//如果需要限制来源，在allowedOrigins中进行设置。
                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
                .allowCredentials(false).maxAge(3600);
    }*/
}
