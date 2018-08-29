package com.ckj.projects.client;

import com.alibaba.fastjson.JSON;
import com.ckj.projects.utils.ObjectAndByte;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;

public class PReciverMsgHandler extends ChannelInboundHandlerAdapter {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        RequestMethod requestMethod= (RequestMethod) o;
        ResponseMethod responseMethod=new ResponseMethod();
        responseMethod.setMsgReq(requestMethod.getMsgReq());
        try{
            logger.info(this.getClass()+"#"+Thread.currentThread().getName()+",参数："+ JSON.toJSONString(channelHandlerContext));
            Class  provider=requestMethod.getMethodClass();
            String pname= provider.getName();
            Class[] paramtypes=requestMethod.getParamTypes();
            Object[] params=requestMethod.getParams();
            Method method=provider.getDeclaredMethod(requestMethod.getMethodName(),paramtypes);
            Object object=ProvideManager.providerMap.get(pname);
            Object retObject=method.invoke(object,params);
            responseMethod.setRespObj(retObject);
            channelHandlerContext.writeAndFlush(responseMethod);
        }catch (Exception e){
            channelHandlerContext.writeAndFlush(responseMethod);
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("异常："+cause.getMessage());
    }
}
