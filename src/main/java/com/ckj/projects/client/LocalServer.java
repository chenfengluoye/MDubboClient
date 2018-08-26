package com.ckj.projects.client;

import com.ckj.projects.config.ConfigUtils;
import com.ckj.projects.config.Constant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;




/**
 * created by ChenKaiJu on 2018/8/2  17:46
 */
public class LocalServer extends Thread{
    ChannelHandlerAdapter l;
    SimpleChannelInboundHandler s;
    ApplicationContext context;

    Logger logger= LoggerFactory.getLogger(LocalServer.class);

    public LocalServer(ApplicationContext context){
        this.context=context;
    }

    ServerSocket serverSocket;

//    @Override
//    public void run() {
//        try{
//            while (true){
//                serverSocket=new ServerSocket(ConfigUtils.getInt(Constant.localServerPort));
//                System.out.println("端口号："+ConfigUtils.getInt(Constant.localServerPort));
//                System.out.println("提供者服务启动。。。");
//                while (true){
//                    Socket socket=serverSocket.accept();
//                    LocalServerHandler localServerHandler=new LocalServerHandler(socket,context);
//                    localServerHandler.start();
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public void run(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new LSChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(ConfigUtils.getInt(Constant.localServerPort)).sync();
            logger.info("netty服务端启动。。。");
            f.channel().closeFuture().sync();
            logger.info("netty服务端启动完毕。。。");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            logger.info("结束了。。。");
        }
    }

}
