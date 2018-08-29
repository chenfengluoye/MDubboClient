package com.ckj.projects.proxy;


import com.ckj.projects.client.Invoker;
import com.ckj.projects.client.RequestMethod;
import com.ckj.projects.client.ServerManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * created by ChenKaiJu on 2018/8/2  10:08
 */
public class SubscriberInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws RuntimeException {
        String methodName=method.getName();
        Class[] paramTypes = method.getParameterTypes();
        if ("toString".equals(methodName) && paramTypes.length == 0) {
            return proxy.toString();
        } else if ("hashCode".equals(methodName) && paramTypes.length == 0) {
            return proxy.hashCode();
        } else if ("equals".equals(methodName) && paramTypes.length == 1) {
            return true;
        }
        try {
            try{
                RequestMethod requestMethod=new RequestMethod();
                requestMethod.setMethodClass(method.getDeclaringClass());
                requestMethod.setParams(args);
                requestMethod.setMethodName(method.getName());
                requestMethod.setParamTypes(method.getParameterTypes());
                requestMethod.setReturnType(method.getReturnType());
                Invoker invoker=new Invoker();
                Object retobj=invoker.invoke(requestMethod);
                return retobj;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
