package com.ckj.projects.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by ChenKaiJu on 2018/8/27  21:56
 */
public class SChannelInitializer extends ChannelInitializer<SocketChannel> {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new ObjectDecoder(1024*1024,ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
        socketChannel.pipeline().addLast(new ObjectEncoder());
        socketChannel.pipeline().addLast(new SReciverMsgHandler());
    }

}
