package com.ckj.projects.client;

import io.netty.channel.Channel;

public class ProviderConfig {

   private Class providerClass;

   private String host;

   private int port;

   private Channel channel;

    public ProviderConfig(){

    }

    public ProviderConfig(Class providerClass,String host,int port){
        this.providerClass=providerClass;
        this.host=host;
        this.port=port;
    }
    public Class getProviderClass() {
        return providerClass;
    }

    public void setProviderClass(Class providerClass) {
        this.providerClass = providerClass;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
