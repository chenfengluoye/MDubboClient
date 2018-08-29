package com.ckj.projects.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LSChannelInitializer extends ChannelInitializer<SocketChannel> {

    Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        try{
            logger.info("新的通道连接进来了。。。。");
            socketChannel.pipeline().addLast(new ObjectDecoder(1024*1024,ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
            socketChannel.pipeline().addLast(new ObjectEncoder());
            socketChannel.pipeline().addLast(new PReciverMsgHandler());

            logger.info("新的通道处理器设置结束。。。。");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
