package com.ckj.projects.praser;

import com.ckj.projects.client.ServerBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * created by ChenKaiJu on 2018/8/28  18:04
 */
public class ServerParser extends AbstractSingleBeanDefinitionParser {


    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    protected Class getBeanClass(Element element) {
        return ServerBean.class;
    }


    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String address=element.getAttribute("ipAddress");
        int port=9000;
        try{
            port= Integer.parseInt(element.getAttribute("port"));
        }catch (Exception e){
            logger.error("端口配置错误，将使用默认端口9000作为本地服务地址。。。。。");
        }
        if(!StringUtils.isEmpty(address))
            ServerBean.setIpAddress(address);
        ServerBean.setPort(port);
    }
}
