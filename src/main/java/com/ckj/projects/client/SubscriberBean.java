package com.ckj.projects.client;

import com.ckj.projects.proxy.SubscriberInvocationHandler;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Proxy;

/**
 * created by ChenKaiJu on 2018/8/5  10:49
 */
public class SubscriberBean<T> implements FactoryBean<T>,InitializingBean {


    T proxy;

    Class<T> refclass;


    @Override
    public void afterPropertiesSet() throws Exception {
        this.proxy=(T)Proxy.newProxyInstance(refclass.getClassLoader(),new Class[]{refclass},new SubscriberInvocationHandler());
    }

    @Override
    public T getObject() throws Exception {
        RegisterClient.registerSubscriber(refclass);
        return proxy;
    }

    @Override
    public Class<T> getObjectType() {
        return refclass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    public Class<T> getRefclass() {
        return refclass;
    }

    public void setRefclass(Class<T> refclass) {
        this.refclass = refclass;
    }

    public T getProxy() {
        return proxy;
    }

    public void setProxy(T proxy) {
        this.proxy = proxy;
    }
}
