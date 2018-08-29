package com.ckj.projects.client;

import com.ckj.projects.utils.ObjectAndByte;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SReciverMsgHandler extends ChannelInboundHandlerAdapter {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext,Object o) throws Exception {
        try{
            ResponseMethod responseMethod= (ResponseMethod)o;
            CallBackService service= channelHandlerContext.channel().attr(RPCChannel.attributeKey).get().remove(responseMethod.getMsgReq());
            service.Call(responseMethod.getRespObj());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("异常："+cause.getMessage());
    }
}
