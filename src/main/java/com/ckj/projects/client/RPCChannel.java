package com.ckj.projects.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RPCChannel {

    static Logger logger= LoggerFactory.getLogger(RPCChannel.class);

    static AttributeKey<Map<String,CallBackService>> attributeKey=AttributeKey.newInstance("dataMap");

    static Map<String,Channel> rpcChannelMap=new HashMap<>();

    public static synchronized Channel getChannel (String host,int port){
        try{
            Channel channel=rpcChannelMap.get(host+":"+port);
            if(channel!=null){
                return channel;
            }
            logger.info("与服务提供者"+host+":"+port+"连接中。。。。。");
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SChannelInitializer());
            ChannelFuture channelFuture = bootstrap.connect(host,port).sync(); // 异步连接服务器
            logger.info("connected..."); // 连接完成
            channel=channelFuture.channel();
            Attribute attribute=channel.attr(attributeKey);
            Map<String,CallBackService> dataMap=new ConcurrentHashMap();
            attribute.set(dataMap);
            channel.attr(attributeKey);
            rpcChannelMap.put(host+":"+port,channel);
            logger.info("与服务提供者"+host+":"+port+"连接完毕。。。。。");
            return channel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
