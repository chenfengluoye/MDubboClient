package com.ckj.projects.client;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LSChannelInitializer extends ChannelInitializer<SocketChannel> {

    Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        logger.info(this.getClass()+"#"+Thread.currentThread().getName()+",参数："+ JSON.toJSONString(socketChannel));
        socketChannel.pipeline().addLast("PReciverMsgHandler",new PReciverMsgHandler());
        logger.info("新的通道连接进来了。。。。");
    }
}
