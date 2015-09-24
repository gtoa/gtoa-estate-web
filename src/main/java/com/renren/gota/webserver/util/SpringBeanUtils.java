package com.renren.gota.webserver.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * 直接获得spring中的bean的工具 <br>
 * 2015年5月14日:下午12:08:44
 * 
 */
public class SpringBeanUtils implements BeanFactoryAware {

    private static BeanFactory beanFactory;

    @Override
    public void setBeanFactory(
                    BeanFactory beanFactory) throws BeansException {
        SpringBeanUtils.beanFactory = beanFactory;
    }

    public static Object getBean(
                    String beanName) {
        return beanFactory.getBean(beanName);
    }

    public static <T> T getBean(
                    Class<T> klass) {
        return beanFactory.getBean(klass);
    }

    public static <T> T getBean(String beanName, Class<T> klass) {
        return beanFactory.getBean(beanName, klass);
    }

}
