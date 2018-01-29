package com.june.config;

import java.net.MalformedURLException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.alibaba.druid.pool.DruidDataSource;

//SpringBoot获取properties配置
//http://blog.csdn.net/yyhjava/article/details/53283675
@Configuration////标注一个类是配置类，spring boot在扫到这个注解时自动加载这个类相关的功能，比如前面的文章中介绍的配置AOP和拦截器时加在类上的Configuration
public class JavaConfig {
	/*JWT*/
	@Value("${jwt.signingSecret}")
	private String signingSecret;
	
	public String getSigningSecret(){
		return this.signingSecret;
	}
	
	/*DataBase*/
	@Value("${spring.datasource.url}")  
    private String dbUrl;   
    @Value("${spring.datasource.username}")  
    private String username;  
    @Value("${spring.datasource.password}")  
    private String password;  
    @Value("${spring.datasource.driverClassName}")  
    private String driverClassName;  
    @Value("${spring.datasource.initialSize}")  
    private int initialSize;   
    @Value("${spring.datasource.minIdle}")  
    private int minIdle;  
    @Value("${spring.datasource.maxActive}")  
    private int maxActive;  
    @Value("${spring.datasource.maxWait}")  
    private int maxWait;  
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")  
    private int timeBetweenEvictionRunsMillis;  
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")  
    private int minEvictableIdleTimeMillis;  
    @Value("${spring.datasource.validationQuery}")  
    private String validationQuery;   
    @Value("${spring.datasource.testWhileIdle}")  
    private boolean testWhileIdle;  
    @Value("${spring.datasource.testOnBorrow}")  
    private boolean testOnBorrow;  
    @Value("${spring.datasource.testOnReturn}")  
    private boolean testOnReturn;  
    @Value("${spring.datasource.poolPreparedStatements}")  
    private boolean poolPreparedStatements;   
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")  
    private int maxPoolPreparedStatementPerConnectionSize;  
    @Value("${spring.datasource.filters}")  
    private String filters;    
    @Value("{spring.datasource.connectionProperties}")  
    private String connectionProperties; 
    
    //在Spring容器中，我们手工注解@Bean 将被优先加载，框架不会重新实例化其他的 实现类。
    @Bean(value="dataSource")     //声明其为Bean实例  
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource  
    public DataSource dataSource(){  
        DruidDataSource datasource = new DruidDataSource();  
        datasource.setUrl(this.dbUrl);  
        datasource.setUsername(username);  
        datasource.setPassword(password);  
        datasource.setDriverClassName(driverClassName);  
        //configuration  
        datasource.setInitialSize(initialSize);  
        datasource.setMinIdle(minIdle);  
        datasource.setMaxActive(maxActive);  
        datasource.setMaxWait(maxWait);  
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
        datasource.setValidationQuery(validationQuery);  
        datasource.setTestWhileIdle(testWhileIdle);  
        datasource.setTestOnBorrow(testOnBorrow);  
        datasource.setTestOnReturn(testOnReturn);  
        datasource.setPoolPreparedStatements(poolPreparedStatements);  
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);  
        try {  
            datasource.setFilters(filters);  
        } catch (SQLException e) {  
            //logger.error("druid configuration initialization filter", e);  
        } 
        datasource.setConnectionProperties(connectionProperties);  
        return datasource;  
    } 

	/*@Bean
	public UsersDaoImpl UsersDaoImpl(JdbcTemplate tmp) {
		return new UsersDaoImpl(tmp);
	}
	
	@Bean
    public UsersBoImpl UsersBoImpl(UsersDaoImpl impl){
         return new UsersBoImpl(impl);
    }
 
    @Bean
    public PersonController PersonController(UsersBoImpl impl){
         return new PersonController(impl);
    }*/
    
    /**
     * 配置实现全局跨域：
     * registry.addMapping("/**")：为根目录的全部请求，也可以设置为"/user/**"，这意味着是user目录下的所有请求。
     * 
	 * 官方文档：	https://docs.spring.io/spring-boot/docs/1.5.6.RELEASE/reference/htmlsingle/#boot-features-cors
     * @return
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
    
    /**
     * websocket配置
     * @return
     */
    @Bean  
    public ServerEndpointExporter serverEndpointExporter(){  
        return new ServerEndpointExporter();  
    }  
    
    
    /*@Bean
    public WebMvcConfigurer corsConfigurer() {
    	System.out.println("1");
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
        		.allowedOrigins("*")
        		.allowedMethods("*")
        		.allowedHeaders("*")
        		//.exposedHeaders("*")
        		.allowCredentials(false).maxAge(3600);
            }
        };
    }
    
    @Bean
	public FilterRegistrationBean corsFilter() {
    	System.out.println("2");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(false);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}*/
    
}
