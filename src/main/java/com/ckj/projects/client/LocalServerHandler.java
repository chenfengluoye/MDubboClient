package com.ckj.projects.client;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.context.ApplicationContext;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * created by ChenKaiJu on 2018/8/4  12:16
 */
public class LocalServerHandler extends Thread {

    Socket socket;

    ApplicationContext context;

    LocalServerHandler(Socket socket,ApplicationContext context){
        this.socket=socket;
        this.context=context;
    }

    @Override
    public void run() {
        try{
            System.out.println("收到服务调用者。。。");
            ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
            RequestMethod requestMethod= (RequestMethod) in.readObject();
            Class  provider=requestMethod.getMethodClass();
            String beanName= provider.getSimpleName();
            Class[] paramtypes=requestMethod.getParamTypes();
            Object[] params=requestMethod.getParams();
            beanName=Character.toLowerCase(beanName.charAt(0))+beanName.substring(1);
            Method method=provider.getDeclaredMethod(requestMethod.getMethodName(),paramtypes);
            Object object=context.getBean(beanName,provider);
            Object retObject=method.invoke(object,params);
            ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(retObject);
            socket.shutdownOutput();
            socket.close();
            System.out.println("方法执行完毕");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
