package com.ckj.projects.client;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ProvideManager {

    public static Map<String,Object> providerMap=new ConcurrentHashMap();

    public static Map<String,List<ProviderConfig>> RPCProviderMap=new ConcurrentHashMap();

    public static ProviderConfig getProviderConfig(Class pclass){
       Set<Map.Entry<String,List<ProviderConfig>>> entries= RPCProviderMap.entrySet();
       for(Map.Entry<String,List<ProviderConfig>> entry:entries){
           List<ProviderConfig> providerConfigs= entry.getValue();
           for(ProviderConfig config:providerConfigs){
               if(pclass.equals(config.getProviderClass())){
                   return config;
               }
           }
       }
       return null;
    }

    public static ProviderConfig getProviderConfig(String key,Class pclass){
        List<ProviderConfig> providerConfigs=RPCProviderMap.get(key);
        if(providerConfigs==null){
            return null;
        }
        for(ProviderConfig config:providerConfigs){
            if(pclass.equals(config.getProviderClass())){
                return config;
            }
        }
        return null;
    }

    public static ProviderConfig getProviderConfig(List<ProviderConfig> providerConfigs,Class pclass){
        if(providerConfigs==null){
            return null;
        }
        for(ProviderConfig config:providerConfigs){
            if(pclass.equals(config.getProviderClass())){
                return config;
            }
        }
        return null;
    }

}
