package com.ckj.projects.praser;

import com.ckj.projects.client.ProviderBean;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * created by ChenKaiJu on 2018/8/5  10:51
 */
public class MDubboPraser extends AbstractSingleBeanDefinitionParser {



    private  Class beanClass;

    private  boolean required;

    public MDubboPraser(Class beanClass, boolean required) {
        this.beanClass = beanClass;
        this.required = required;
    }

    protected Class getBeanClass(Element element) {
        return beanClass;
    }

    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        bean.addPropertyValue("refclass",element.getAttribute("interface"));
        bean.setLazyInit(false);
        if(element.hasAttribute("ref")){
            String ref=element.getAttribute("ref");
            Object refbean=new RuntimeBeanReference(ref);
            bean.addPropertyValue("object",refbean);
        }
    }


}
