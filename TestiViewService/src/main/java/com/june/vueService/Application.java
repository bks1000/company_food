package com.june.vueService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//入口
//@EnableAutoConfiguration  //启用自动配置 该框架就能够进行行为的配置，以引导应用程序的启动与运行, 根据导入的starter-pom 自动加载配置
@EnableTransactionManagement // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@ComponentScan(value="com.june")  //扫描组件 @ComponentScan(value = "com.spriboot.controller") 配置扫描组件的路径
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
