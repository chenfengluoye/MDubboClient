package com.ckj.projects.client;

import java.util.Map;
/**
 * created by ChenKaiJu on 2018/8/4  9:46
 */
public class RegisterClient {




    public static void registerSubscriber(Class subscriberIntf){
       LocalServerToCenter.registerSubscriber(subscriberIntf);
    }


    public static void registerProvide(Class provideIntf){
        LocalServerToCenter.registerProvider(provideIntf);
    }


}
