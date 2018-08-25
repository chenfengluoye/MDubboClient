package com.ckj.projects.inhandler;


import com.ckj.projects.client.ProviderBean;
import com.ckj.projects.client.SubscriberBean;
import com.ckj.projects.praser.IndexServerParser;
import com.ckj.projects.praser.MDubboPraser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Homepage
 */
public class MDubboNameSpaceHandler extends NamespaceHandlerSupport {
	public void init() {
		registerBeanDefinitionParser("indexServer", new IndexServerParser());
		registerBeanDefinitionParser("subscriber", new MDubboPraser(SubscriberBean.class,true));
		registerBeanDefinitionParser("provider", new MDubboPraser(ProviderBean.class,true));
	}

}
