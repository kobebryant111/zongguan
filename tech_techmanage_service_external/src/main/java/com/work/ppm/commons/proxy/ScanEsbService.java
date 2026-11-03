package com.work.ppm.commons.proxy;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;

import com.work.ppm.commons.annotaion.esb.EsbService;
import com.work.ppm.commons.handler.EsbHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * esb实现类注入
 *
 */
@Component
@Slf4j
public class ScanEsbService implements BeanDefinitionRegistryPostProcessor  {

    /**
     * 扫描包名
     */
    private final static String PPACKAGE = "com.work.ppm";

    /**
     * 接口属性
     */
    private final static String FIELD_INTERFACE_CLASS = "interfaceClass";

    /**
     * 处理器属性
     */
    private final static String FIELD_HANDLER = "handler";

    /**
     * 将接口实现类注入到spring中
     * @param beanDefinitionRegistry
     */
    public void registry(BeanDefinitionRegistry beanDefinitionRegistry){
        Set<Class<?>> scanPackage = ClassUtil.scanPackageByAnnotation(
                PPACKAGE, EsbService.class);
        for(Class cls : scanPackage){
            log.info("注入esb服务{}",cls.getSimpleName());
//            EsbService esbService = (EsbService) cls.getAnnotation(EsbService.class);
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(cls);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            //设置属性
            definition.getPropertyValues().add(FIELD_INTERFACE_CLASS,cls);
            definition.getPropertyValues().add(FIELD_HANDLER,new EsbHandler());
            //动态代理实现类
            definition.setBeanClass(ProxyFactory.class);
            //注入模式
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            //对象名称
            String beanName = StrUtil.removePreAndLowerFirst(cls.getSimpleName(),0);
            //注入到spring
            beanDefinitionRegistry.registerBeanDefinition(beanName,definition);
        }

    }

    /**
     * 对象注册到spring中
     * @param beanDefinitionRegistry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry)
            throws BeansException {
        registry(beanDefinitionRegistry);
    }

    /**
     * 获取spring中的对象
     * @param configurableListableBeanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory)
            throws BeansException {
        log.info("postProcessBeanFactory 回调");
    }

}
