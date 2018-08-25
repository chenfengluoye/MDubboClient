package com.ckj.projects.client;

import com.ckj.projects.config.ConfigUtils;
import com.ckj.projects.config.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * created by ChenKaiJu on 2018/8/2  17:46
 */
public class LocalServer extends Thread{

    ApplicationContext context;

    Logger logger= LoggerFactory.getLogger(LocalServer.class);

    public LocalServer(ApplicationContext context){
        this.context=context;
    }

    ServerSocket serverSocket;


    @Override
    public void run() {
        try{
            while (true){
                serverSocket=new ServerSocket(ConfigUtils.getInt(Constant.localServerPort));
                System.out.println("端口号："+ConfigUtils.getInt(Constant.localServerPort));
                System.out.println("提供者服务启动。。。");
                while (true){
                    Socket socket=serverSocket.accept();
                    LocalServerHandler localServerHandler=new LocalServerHandler(socket,context);
                    localServerHandler.start();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
