package com.ckj.projects.client;

import org.apache.zookeeper.AsyncCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MStringCallback implements AsyncCallback.StringCallback {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    public void processResult(int i, String s, Object o, String s1) {
        logger.info(o.toString()+"执行完成");
    }
}
