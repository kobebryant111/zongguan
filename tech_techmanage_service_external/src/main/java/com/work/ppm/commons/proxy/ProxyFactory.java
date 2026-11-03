package com.work.ppm.commons.proxy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * esb服务代理实现
 *
 */
@Getter
@Setter
public class ProxyFactory<T> implements FactoryBean<T> {

    /**
     * 被代理对象
     */
    private Class<T> interfaceClass;

    /**
     * 处理方法
     */
    private InvocationHandler handler;


    /**
     * 获得代理对象
     * @return T
     * @throws Exception
     */
    @Override
    public T getObject() throws Exception {
        final Class[] interfaces = {interfaceClass};
        return (T)Proxy.newProxyInstance(this.getClass().getClassLoader(),interfaces,handler);
    }

    /**
     * 对象类型
     * @return Class
     */
    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

}
