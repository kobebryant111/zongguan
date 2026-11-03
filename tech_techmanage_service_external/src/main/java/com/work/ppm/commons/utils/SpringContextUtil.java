package com.work.ppm.commons.utils;

import com.work.mercury.log.annotation.MercuryLog;
import com.work.mercury.log.config.MercuryLogConstant;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * SpringContext
 *
 * 2019/8/9 14:51
 */
@MercuryLog(MercuryLogConstant.TYPE_SIMPLE_LOGGER)
public class SpringContextUtil implements ApplicationContextAware {

    /**
     * applicationContext
     */
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }


    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /**
     * 获取bean
     *
     * @param requiredType Class
     * @param <T>          T
     * @return T
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 获取bean
     *
     * @param beanName String
     * @param <T>      T
     * @return T
     */
    public static <T> T getBean(String beanName) {
        assertContextInjected();
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 判断applicationContext是否注入
     */
    private static void assertContextInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException();
        }
    }
}
