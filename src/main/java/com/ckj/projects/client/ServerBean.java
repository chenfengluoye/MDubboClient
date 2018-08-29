package com.ckj.projects.client;

import org.springframework.beans.factory.InitializingBean;

/**
 * created by ChenKaiJu on 2018/8/28  17:58
 */
public class ServerBean implements InitializingBean {

    public static String ipAddress=null;

    public static int port=9000;

    public static String getIpAddress() {
        return ipAddress;
    }

    public static void setIpAddress(String ipAddress) {
        ServerBean.ipAddress = ipAddress;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        ServerBean.port = port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
