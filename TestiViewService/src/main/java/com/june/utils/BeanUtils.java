package com.june.utils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;
    private static BeanUtils beanUtils = null;
    
    public static BeanUtils getInstance(){
      Lock lock = new ReentrantLock();
      try {
        lock.lock();
        if (beanUtils == null) {
        	beanUtils = (BeanUtils)applicationContext.getBean(BeanUtils.class);
        }
      } finally {
        lock.unlock();
      }
      return beanUtils;
    }
    
    public Object getBean(String beanName){
      return applicationContext.getBean(beanName);
    }
    
    public <T> T getBean(String beanName, Class<T> type){
      return applicationContext.getBean(beanName, type);
    }
    
    public <T> T getBean(Class<T> type){
      return applicationContext.getBean(type);
    }

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BeanUtils.applicationContext = applicationContext;
	}
}
