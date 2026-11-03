package com.work.ppm.commons.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * spring 工具
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    /**
     * spring上下文
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    /**
     * 获取bean
     * @param beanId beanId
     * @return  Object
     * @throws BeansException BeansException
     */
    public static Object getBean(String beanId) throws BeansException {
        return applicationContext.getBean(beanId);
    }

    /**
     * 获取bean
     * @param beanId beanId
     * @param clazz 类
     * @return  T
     * @throws BeansException BeansException
     */
    public static<T> T getBean(String beanId, Class<T> clazz) throws BeansException {
        return applicationContext.getBean(beanId,clazz);
    }

    /**
     * 获取bean
     * @param clazz 类
     * @return T
     * @throws  BeansException
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return applicationContext.getBean(clazz);
    }

    /**
     * getRequest
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return request;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
