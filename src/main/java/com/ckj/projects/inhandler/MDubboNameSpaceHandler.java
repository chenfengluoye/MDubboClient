package com.ckj.projects.inhandler;


import com.ckj.projects.client.ProviderBean;
import com.ckj.projects.client.SubscriberBean;
import com.ckj.projects.praser.IndexServerParser;
import com.ckj.projects.praser.MDubboParser;
import com.ckj.projects.praser.ServerParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Homepage
 */
public class MDubboNameSpaceHandler extends NamespaceHandlerSupport {
	public void init() {
		registerBeanDefinitionParser("server",new ServerParser());
		registerBeanDefinitionParser("indexServer", new IndexServerParser());
		registerBeanDefinitionParser("subscriber", new MDubboParser(SubscriberBean.class,true));
		registerBeanDefinitionParser("provider", new MDubboParser(ProviderBean.class,true));
	}

}
