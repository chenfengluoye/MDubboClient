package com.ckj.projects.client;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;


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

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new LSChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            String address=ServerBean.getIpAddress();
            ChannelFuture future=null;
            int port=ServerBean.getPort();
            if(StringUtils.isEmpty(address)){
                future= b.bind(port).sync();
            }else{
                future= b.bind(new InetSocketAddress(address,port)).sync();
            }
            logger.info("netty服务端启动。。。，port:" +port);
            logger.info("netty服务端启动完毕。。。");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("服务端关闭了。。。");
        }
    }

}
