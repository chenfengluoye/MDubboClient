package com.ckj.projects.client;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;

public class PReciverMsgHandler extends SimpleChannelInboundHandler {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        logger.info(this.getClass()+"#"+Thread.currentThread().getName()+",参数："+ JSON.toJSONString(channelHandlerContext));
        RequestMethod requestMethod= (RequestMethod) o;
        Class  provider=requestMethod.getMethodClass();
        String pname= provider.getName();
        Class[] paramtypes=requestMethod.getParamTypes();
        Object[] params=requestMethod.getParams();
        Method method=provider.getDeclaredMethod(requestMethod.getMethodName(),paramtypes);
        Object object=ProvideManager.providerMap.get(pname);
        Object retObject=method.invoke(object,params);
        channelHandlerContext.write(retObject);
        channelHandlerContext.flush();
        System.out.println("方法执行完毕");
    }
}
