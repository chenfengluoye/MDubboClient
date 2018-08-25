package com.ckj.projects.client;

import java.util.Map;
/**
 * created by ChenKaiJu on 2018/8/4  9:46
 */
public class RegisterClient {




    public static void registerSubscriber(Class subscriberIntf){

        for(Map.Entry<String, LocalServerToCenter> center:ServerManager.centerList.entrySet()){
            center.getValue().registrySubscrier(subscriberIntf.getName());
        }
    }


    public static void registerProvide(Class provideIntf){

        for(Map.Entry<String, LocalServerToCenter> center:ServerManager.centerList.entrySet()){
            center.getValue().registryProvider(provideIntf.getName());
        }
    }


}
