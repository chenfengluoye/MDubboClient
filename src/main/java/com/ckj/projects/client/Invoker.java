package com.ckj.projects.client;

import com.ckj.projects.utils.ObjectAndByte;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

public class Invoker {

    Logger logger= LoggerFactory.getLogger(this.getClass());
    public Object invoke(RequestMethod method){
        String msgReq=UUID.randomUUID().toString();
        method.setMsgReq(msgReq);
        CallBackService callBackService=new CallBackService();
        ProviderConfig config=ProvideManager.getProviderConfig(method.getMethodClass());
        if(config==null){
            logger.error("no providers for name "+method.getMethodClass());
            return null;
        }
        config.getChannel().attr(RPCChannel.attributeKey).get().put(msgReq,callBackService);
        config.getChannel().writeAndFlush(method);
        synchronized (callBackService){
            try {
                callBackService.wait(10000);
                if(callBackService.isTimeOut){
                    logger.error("the provider doesn't response the result for too long time .........");
                    return null;
                }
                Object retObj=callBackService.getRetObj();
                return retObj;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }
    }



}
