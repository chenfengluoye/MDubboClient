package com.ckj.projects.proxy;


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
            System.out.println(method.getDeclaringClass().getName()+"#"+method.getName()+"#Subscriber方法开始执行。。。");
            Class cl=method.getDeclaringClass();
            String className=cl.getName();
            List<Map<String,Object>> providers=ServerManager.provideList.get(className);
            if(providers==null||providers.size()==0){
                throw new RuntimeException(method.getName()+"&& no aliable provider...");
            }
            for(int i=0;i<providers.size();i++){
                try{
                    Map<String,Object> provider=providers.get(i);
                    String address= (String) provider.get("ipAddress");
                    int port=Integer.valueOf((Integer) provider.get("serverPort"));
                    Socket socket=new Socket(address,port);
                    ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
                    RequestMethod invoker=new RequestMethod();
                    invoker.setMethodClass(method.getDeclaringClass());
                    invoker.setParams(args);
                    invoker.setMethodName(method.getName());
                    invoker.setParamTypes(method.getParameterTypes());
                    invoker.setReturnType(method.getReturnType());
                    outputStream.writeObject(invoker);
                    socket.shutdownOutput();
                    ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
                    Object object= inputStream.readObject();
                    inputStream.close();
                    socket.close();
                    return object;
                }catch (Exception e){
                    System.out.println("此提供者执行任务异常"+providers.get(i).get("ipAddress")+":"+providers.get(i).get("serverPort"));
                }
            }
            System.out.println(method.getName()+"#Subscriber方法执行完毕。。。");
            return null;
        }catch (Exception e){
            System.out.println(method.getName()+"#Subscriber方法异常结束。。。");
            e.printStackTrace();
            return null;
        }
    }
}
