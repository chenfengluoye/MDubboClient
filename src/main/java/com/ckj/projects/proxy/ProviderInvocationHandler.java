package com.ckj.projects.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * created by ChenKaiJu on 2018/8/2  18:08
 */
public class ProviderInvocationHandler  implements InvocationHandler {

    Object realObj;

    public ProviderInvocationHandler(Object realObj){
        this.realObj=realObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getDeclaringClass().getName()+"#"+method.getName()+"#Provider方法开始执行。。。");
        try {
            Object ob=method.invoke(realObj,args);
            System.out.println(method.getName()+"#Provider方法执行完毕。。。");
            return ob;
        }catch (Exception e){
            System.out.println(method.getName()+"#Provider方法异常结束。。。");
            e.printStackTrace();
            return null;
        }
    }
}
