package com.ckj.projects.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RPCChannel {

    static Logger logger= LoggerFactory.getLogger(RPCChannel.class);

    static AttributeKey<Map<String,CallBackService>> attributeKey=AttributeKey.newInstance("dataMap");



    public static synchronized Channel getChannel (String host,int port){
        try{
            Channel channel=null;
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new SReciverMsgHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(host,port).sync(); // 异步连接服务器
            logger.info("connected..."); // 连接完成
            channel=channelFuture.channel();
            channel.closeFuture().sync(); // 异步等待关闭连接channel
            Attribute attribute=channel.attr(attributeKey);
            Map<String,CallBackService> dataMap=new ConcurrentHashMap<String, CallBackService>();
            attribute.set(dataMap);
            channel.attr(attributeKey);
            logger.info("closed.."); // 关闭完成
            return channel;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
