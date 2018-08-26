package com.ckj.projects.client;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

public class Invoker {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    public Object invoke(RequestMethod method){
        logger.info("方法执行开始。。。。"+ JSON.toJSONString(method));
        String msgReq=UUID.randomUUID().toString();
        CallBackService callBackService=new CallBackService();
        ProviderConfig config=ProvideManager.getProviderConfig(method.getMethodClass());
        config.getChannel().attr(RPCChannel.attributeKey).get().put(msgReq,callBackService);
        config.getChannel().writeAndFlush(method);
        synchronized (callBackService){
            try {
                callBackService.wait();
                Object retObj=callBackService.getRetObj();
                logger.info("方法执行完毕。。。。"+ JSON.toJSONString(retObj));
                return retObj;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }
    }



}
