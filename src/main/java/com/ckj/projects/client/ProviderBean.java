package com.ckj.projects.client;

import com.ckj.projects.proxy.ProviderInvocationHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * created by ChenKaiJu on 2018/8/4  17:40
 */
public class ProviderBean<T> implements FactoryBean<T>,InitializingBean,ApplicationContextAware {

    static ApplicationContext context;

    static boolean isruning=false;

    T object;

    T proxy;

    Class<T> refclass;


    @Override
    public void afterPropertiesSet() throws Exception {
        InvocationHandler handler=new ProviderInvocationHandler(object);
        Class[]interfaces=object.getClass().getInterfaces();
        ClassLoader loader=refclass.getClassLoader();
        if (!isruning){
            startClient();
            isruning=true;
        }
        RegisterClient.registerProvide(refclass);
        this.proxy=(T)Proxy.newProxyInstance(loader,interfaces,handler);
        ProvideManager.providerMap.put(refclass.getName(),this.proxy);
    }

    @Override
    public T getObject() throws Exception {

        return this.proxy;
    }

    @Override
    public Class<T> getObjectType() {

        return refclass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public Class<T> getRefclass() {
        return refclass;
    }

    public void setRefclass(Class<T> refclass) {
        this.refclass = refclass;
    }

    public static  void  startClient(){
        LocalServer localServer=new LocalServer(context);
        localServer.start();
        System.out.println("客户端启动。。。");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
    }
}
