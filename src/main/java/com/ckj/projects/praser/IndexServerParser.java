package com.ckj.projects.praser;

import com.ckj.projects.client.ClusterChooseClient;
import com.ckj.projects.client.IndexServerBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
/**
 * created by ChenKaiJu on 2018/8/2  14:55
 */
public class IndexServerParser extends AbstractSingleBeanDefinitionParser{

    @Override
    protected Class getBeanClass(Element element) {
        return IndexServerBean.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String address=element.getAttribute("address");
        if(!StringUtils.isEmpty(address))
        ClusterChooseClient.address=address;
        ClusterChooseClient.pullRegistryCentry();
    }

}
