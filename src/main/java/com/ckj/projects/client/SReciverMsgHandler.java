package com.ckj.projects.client;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SReciverMsgHandler extends SimpleChannelInboundHandler {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    public static ChannelHandlerContext channelHandlerContext;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        this.channelHandlerContext=channelHandlerContext;
        logger.info(this.getClass()+"#"+Thread.currentThread().getName()+",参数："+ JSON.toJSONString(o));

    }
}
