package com.june.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * 事务配置
 * 1.在入库处添加@EnableTransactionManagement // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
 * 2.在配置类中配置事务类
 * 3.在需要使用事务的类或方法上，添加注解 @Transactional
 * // 使用value具体指定使用哪个事务管理器
 * // 在存在多个事务管理器的情况下，如果使用value具体指定
   // 则默认使用方法 annotationDrivenTransactionManager() 返回的事务管理器
   
   	参考出处：http://blog.csdn.net/catoop/article/details/50595702
   
   	注：如果Spring容器中存在多个 PlatformTransactionManager 实例，
   	并且没有实现接口 TransactionManagementConfigurer 指定默认值，在我们在方法上使用注解 @Transactional 的时候，
   	就必须要用value指定，如果不指定，则会抛出异常。

	对于系统需要提供默认事务管理的情况下，实现接口 TransactionManagementConfigurer 指定。
 * @author lenovo
 *
 */
@Configuration
public class TransactionConfig implements TransactionManagementConfigurer {

	@Resource(name="txManager1")
    private PlatformTransactionManager txManager1;

    // 创建事务管理器1
    @Bean(name = "txManager1")
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    // 创建事务管理器2
    /*@Bean(name = "txManager2")
    public PlatformTransactionManager txManager2(EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }*/

    // 实现接口 TransactionManagementConfigurer 方法，其返回值代表在拥有多个事务管理器的情况下默认使用的事务管理器
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager1;
    }

}
