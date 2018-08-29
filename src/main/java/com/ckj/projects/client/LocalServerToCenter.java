package com.ckj.projects.client;

import com.ckj.projects.config.ConfigUtils;
import com.ckj.projects.config.Constant;
import com.ckj.projects.utils.ZookeeperUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.json.JSONObject;
import java.net.*;
import java.util.*;

/**
 * created by ChenKaiJu on 2018/8/4  11:17
 */
public class LocalServerToCenter{

    public static  boolean  checkedRoot=false;

    public static boolean checkedProvider=false;

    public static boolean checkedSubscriber=false;

    private static Logger logger = Logger.getLogger(LocalServerToCenter.class);


    public static void registerProvider(Class pclass){
        ZooKeeper zooKeeper=ZookeeperUtils.getProviderZooKeeper(ClusterChooseClient.centryAddrList);
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip=addr.getHostAddress();
        String node="{"+pclass.getName()+"}["+ip+"]#"+ConfigUtils.getInt(Constant.localServerPort)+"#";
        byte[] bytes={1,1,1};
        try {
            if(!checkedRoot){
                if(zooKeeper.exists("/MDubbo",false)==null){
                    String result=zooKeeper.create("/MDubbo", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
                checkedRoot=true;
            }
            if(!checkedProvider){
                if(zooKeeper.exists("/MDubbo/providers",false)==null){
                    zooKeeper.create("/MDubbo/providers", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
                checkedProvider=true;
            }
            if(zooKeeper.exists("/MDubbo/providers/"+node,false)==null){
                String result=zooKeeper.create("/MDubbo/providers/" +node, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                logger.info("注册服务提供者"+pclass.getName()+"注册结果："+result);
            }
            logger.info("注册服务提供者"+pclass.getName()+"注册结完毕");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            String node="{"+sclass.getName()+"}["+ip+"]";
            if(!checkedRoot){
                if(zooKeeper.exists("/MDubbo",false)==null){
                    String result=zooKeeper.create("/MDubbo", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
                checkedRoot=true;
            }
            if(!checkedSubscriber){
                if(zooKeeper.exists("/MDubbo/subscribers",false)==null){
                    zooKeeper.create("/MDubbo/subscribers", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
                checkedSubscriber=true;
            }
            if(zooKeeper.exists("/MDubbo/subscribers"+node,false)==null){
                String result=zooKeeper.create("/MDubbo/subscribers/" + node, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                logger.info("消费者"+sclass.getName()+"注册结果："+result);
            }
            logger.info("消费者"+sclass.getName()+"注册完毕");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            List<String> providers=zooKeeper.getChildren("/MDubbo/providers",ZookeeperUtils.subcriberWatcher);
            logger.info("获得服务提供者结果："+Arrays.toString(providers.toArray()));
            for(String str:providers){
                logger.info(str);
                try {
                    String pip=str.substring(str.indexOf("[")+1,str.indexOf("]"));
                    int pport=Integer.valueOf(str.substring(str.indexOf("#")+1,str.lastIndexOf("#")));
                    String pcname=str.substring(str.indexOf("{")+1,str.indexOf("}"));
                    Class pclass=Class.forName(pcname);
                    if(pclass.equals(sclass)){
                        List<ProviderConfig> providerConfigs=ProvideManager.RPCProviderMap.get(pip+":"+pclass);
                        if(providerConfigs==null){
                            providerConfigs=new ArrayList<ProviderConfig>();
                        }
                        if(ProvideManager.getProviderConfig(providerConfigs,pclass)==null){
                            ProviderConfig pconfigObj=new ProviderConfig();
                            pconfigObj.setHost(pip);
                            pconfigObj.setPort(pport);
                            pconfigObj.setProviderClass(pclass);
                            pconfigObj.setChannel(RPCChannel.getChannel(pip,pport));
                            providerConfigs.add(pconfigObj);
                        }
                        ProvideManager.RPCProviderMap.put(pip+":"+pclass,providerConfigs);
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

}
