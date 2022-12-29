package com.splendor.notes.infrastructure.status.service;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;

/**
 * @author splendor.s
 * @create 2022/12/29 下午7:04
 * @description
 */
public class BeanFactoryUtil implements ApplicationContextAware {

    public static <T> T getBean(Class className){
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
