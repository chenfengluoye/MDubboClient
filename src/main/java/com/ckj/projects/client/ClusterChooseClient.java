package com.ckj.projects.client;

import com.ckj.projects.utils.NetUtils;
import org.json.JSONArray;

/**
 * created by ChenKaiJu on 2018/8/2  17:45
 */
public class ClusterChooseClient {

    public static String address="http://localhost:8080/MDubboIndexServer/IndexServer";

    public static JSONArray centryAddrList=new JSONArray();

    public static void pullRegistryCentry() throws RuntimeException {
        try{
            System.out.println("索引服务启动，获取注册中心地址和端口:.........\n索引地址："+address);
            String result=NetUtils.httpGetRequest(address);
            centryAddrList=new JSONArray(result);
            System.out.println("与注册中心集群建立通完成。。。");
        }catch (Exception e){
            e.printStackTrace();
            throw (new RuntimeException(e.getMessage()));
        }
    }
}
