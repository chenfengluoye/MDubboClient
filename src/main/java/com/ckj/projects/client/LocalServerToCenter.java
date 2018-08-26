package com.ckj.projects.client;

import com.ckj.projects.config.ConfigUtils;
import com.ckj.projects.config.Constant;
import com.ckj.projects.utils.NetUtils;
import com.ckj.projects.utils.ZookeeperUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * created by ChenKaiJu on 2018/8/4  11:17
 */
public class LocalServerToCenter{


    private Logger logger = Logger.getLogger(this.getClass());

//    Socket socket;
//
//    BufferedReader reader;
//
//    BufferedWriter writer;



    public static void registerProvider(Class pclass){
        ZooKeeper zooKeeper=ZookeeperUtils.getProviderZooKeeper(ClusterChooseClient.centryAddrList);
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip=addr.getHostAddress();
        JSONObject providerJson=new JSONObject();
        providerJson.put("ipAddress",ip);
        providerJson.put("serverPort",ConfigUtils.getInt(Constant.localServerPort));
        providerJson.put("provider",pclass.getName());
        byte[] bytes={1,1,1};
        List<ACL> acls=new ArrayList<ACL>();
        zooKeeper.create("/MDubbo/providers/" + providerJson.toString(), bytes, acls, CreateMode.EPHEMERAL,new MStringCallback(),"注册服务提供者"+pclass.getName());
    }


    public static void registerSubscriber(Class sclass){
        ZooKeeper zooKeeper=ZookeeperUtils.getSubcriberZookeeper(ClusterChooseClient.centryAddrList);
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip=addr.getHostAddress();
        JSONObject providerJson=new JSONObject();
        providerJson.put("ipAddress",ip);
        providerJson.put("subscriber",sclass.getName());
        byte[] bytes={1,1,1};
        List<ACL> acls=new ArrayList<ACL>();
        zooKeeper.create("/MDubbo/subscribers/" + providerJson.toString(), bytes, acls, CreateMode.EPHEMERAL,new MStringCallback(),"注册服务提供者"+sclass.getName()+"创建");
        try {
            List<String> providers=zooKeeper.getChildren("/MDubbo/providers",ZookeeperUtils.subcriberWatcher);
            for(String str:providers){
                JSONObject pconfig=new JSONObject(str);
                String pip=pconfig.getString("ipAddress");
                int pport=pconfig.getInt("serverPort");
                try {
                    Class pclass=Class.forName(pconfig.getString("provider"));
                    if(pclass.equals(sclass)){
                        List<ProviderConfig> providerConfigs=ProvideManager.RPCProviderMap.get(pip+":"+pclass);
                        if(providerConfigs==null){
                            providerConfigs=new ArrayList<ProviderConfig>();
                        }
                        if(ProvideManager.getProviderConfig(providerConfigs,pclass)!=null){
                            ProviderConfig pconfigObj=new ProviderConfig();
                            pconfigObj.setHost(pip);
                            pconfigObj.setPort(pport);
                            pconfigObj.setProviderClass(pclass);
                            pconfigObj.setChannel(RPCChannel.getChannel(pip,pport));
                            providerConfigs.add(pconfigObj);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }










//
//
//
//
//
//
//
//
//    public LocalServerToCenter(Socket socket){
//        try {
//            this.socket=socket;
//            reader=new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
//            writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
//        }catch (Exception e){
//        }
//    }
//
//    public synchronized void  registryProvider(String intface){
//        try {
//            JSONObject object=new JSONObject();
//            object.put("dowhat","registryProvider");
//            object.put("intface",intface);
//            object.put("serverPort",ConfigUtils.getInt(Constant.localServerPort));
//            writer.write(object.toString());
//            writer.write("\nstop\n");
//            writer.flush();
//            String resp=reader.readLine();
//            if("registerSuccess".equals(resp)){
//                System.out.println(intface+"服务提供者者在注册中心"+socket.getRemoteSocketAddress()+":"+socket.getPort()+"注册成功");
//            }else{
//                System.out.println(intface+"服务提供者在注册中心"+socket.getRemoteSocketAddress()+":"+socket.getPort()+"注册失败，原因："+resp);
//            }
//        } catch (IOException e) {
//            System.out.println(intface+"服务提供者在注册中心"+socket.getRemoteSocketAddress()+":"+socket.getPort()+"注册失败，原因："+e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    public synchronized void registrySubscrier(String intface){
//        try {
//            Class intclass= Class.forName(intface);
//            JSONObject object=new JSONObject();
//            object.put("dowhat","registerSubscriber");
//            object.put("intface",intface);
//            writer.write(object.toString());
//            writer.write("\nstop\n");
//            writer.flush();
//            String resp=reader.readLine();
//            JSONArray provides=new JSONArray(resp);
//            List<Map<String,Object>> Intfprovides=ServerManager.provideList.get(intclass.getName());
//            if(Intfprovides==null){
//                Intfprovides=new ArrayList<Map<String, Object>>();
//                ServerManager.provideList.put(intclass.getName(),Intfprovides);
//            }
//            for(int j=0;j<provides.length();j++){
//                JSONObject provider=provides.getJSONObject(j);
//                Map<String,Object> pro=new HashMap();
//                pro.put("ipAddress",provider.getString("ipAddress"));
//                pro.put("serverPort",provider.getInt("serverPort"));
//                Intfprovides.add(pro);
//            }
//            System.out.println(intface+"服务使用者在注册中心"+socket.getInetAddress().getHostName()+":"+socket.getPort()+"注册成功");
//        }catch (Exception e){
//            System.out.println(intface+"服务使用者在注册中心"+socket.getRemoteSocketAddress()+":"+socket.getPort()+"注册失败，原因："+e.getMessage());
//            e.printStackTrace();
//        }
//    }
}
