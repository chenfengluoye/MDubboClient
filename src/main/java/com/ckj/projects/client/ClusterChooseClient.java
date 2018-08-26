package com.ckj.projects.client;

import com.ckj.projects.utils.NetUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.Socket;
import java.util.*;

/**
 * created by ChenKaiJu on 2018/8/2  17:45
 */
public class ClusterChooseClient {

    public static String address="127.0.0.1:8086/MDubbo/getIndexes";

    public static JSONArray centryAddrList=new JSONArray();

    public static void pullRegistryCentry() throws RuntimeException {
        try{
            System.out.println("索引服务启动，获取注册中心地址和端口:.........\n索引地址："+address);
            String result=NetUtils.httpGetRequest(address);
            centryAddrList=new JSONArray(result);
//            initRegistryCenters();
            System.out.println("与注册中心集群建立通完成。。。");
        }catch (Exception e){
            e.printStackTrace();
            throw (new RuntimeException(e.getMessage()));
        }
    }

//    public static void initRegistryCenters(){
//        for(int i=0;i<ClusterChooseClient.centryAddrList.length();i++){
//            try {
//                JSONObject jsonObject=ClusterChooseClient.centryAddrList.getJSONObject(i);
//                String address=jsonObject.getString("ipAddress");
//                int port=jsonObject.getInt("serverPort");
//                Socket socket=new Socket(address,port);
//                LocalServerToCenter center=new LocalServerToCenter(socket);
//                ServerManager.centerList.put(address+":"+port,center);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }



}
