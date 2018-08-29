package com.ckj.projects.utils;

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.*;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Random;


public class ZookeeperUtils {

    private static Logger logger = Logger.getLogger(ZookeeperUtils.class);

    public static ZooKeeper providerZooKeeper=null;

    public static ZooKeeper subcriberZookeeper=null;

    public static Watcher subcriberWatcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            if(event.getType().equals(Event.EventType.NodeChildrenChanged)){
                logger.info("消费端监听到注册中心有事件发生" + JSON.toJSONString(event));
                try {
                    subcriberZookeeper.getChildren("/Mdubbo/providers",subcriberWatcher);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public static ZooKeeper zookeeperInit(JSONArray centerList) {
        ZooKeeper zooKeeper=null;
        Random random=new Random();
        int i= random.nextInt(centerList.length());
        JSONObject center=centerList.getJSONObject(i);
        try {
            zooKeeper = new ZooKeeper(center.getString("ipAddress")+":"+center.getInt("serverPort"), 20000 , subcriberWatcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zooKeeper;
    }

    public static ZooKeeper getProviderZooKeeper(JSONArray centerList) {
        if(providerZooKeeper==null){
            providerZooKeeper=zookeeperInit(centerList);
        }
        return providerZooKeeper;
    }

    public static ZooKeeper getSubcriberZookeeper(JSONArray centerList) {
        if(subcriberZookeeper==null){
            subcriberZookeeper=zookeeperInit(centerList);
        }
        return subcriberZookeeper;
    }

}
