package com.ckj.projects.client;

import com.ckj.projects.config.ConfigUtils;
import com.ckj.projects.config.Constant;
import com.ckj.projects.utils.NetUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by ChenKaiJu on 2018/8/4  11:17
 */
public class LocalServerToCenter{

    Socket socket;

    BufferedReader reader;

    BufferedWriter writer;

    public LocalServerToCenter(Socket socket){
        try {
            this.socket=socket;
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
            writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
        }catch (Exception e){
        }
    }

    public synchronized void  registryProvider(String intface){
        try {
            JSONObject object=new JSONObject();
            object.put("dowhat","registryProvider");
            object.put("intface",intface);
            object.put("serverPort",ConfigUtils.getInt(Constant.localServerPort));
            writer.write(object.toString());
            writer.write("\nstop\n");
            writer.flush();
            String resp=reader.readLine();
            if("registerSuccess".equals(resp)){
                System.out.println(intface+"服务提供者者在注册中心"+socket.getRemoteSocketAddress()+":"+socket.getPort()+"注册成功");
            }else{
                System.out.println(intface+"服务提供者在注册中心"+socket.getRemoteSocketAddress()+":"+socket.getPort()+"注册失败，原因："+resp);
            }
        } catch (IOException e) {
            System.out.println(intface+"服务提供者在注册中心"+socket.getRemoteSocketAddress()+":"+socket.getPort()+"注册失败，原因："+e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void registrySubscrier(String intface){
        try {
            Class intclass= Class.forName(intface);
            JSONObject object=new JSONObject();
            object.put("dowhat","registerSubscriber");
            object.put("intface",intface);
            writer.write(object.toString());
            writer.write("\nstop\n");
            writer.flush();
            String resp=reader.readLine();
            JSONArray provides=new JSONArray(resp);
            List<Map<String,Object>> Intfprovides=ServerManager.provideList.get(intclass.getName());
            if(Intfprovides==null){
                Intfprovides=new ArrayList<Map<String, Object>>();
                ServerManager.provideList.put(intclass.getName(),Intfprovides);
            }
            for(int j=0;j<provides.length();j++){
                JSONObject provider=provides.getJSONObject(j);
                Map<String,Object> pro=new HashMap();
                pro.put("ipAddress",provider.getString("ipAddress"));
                pro.put("serverPort",provider.getInt("serverPort"));
                Intfprovides.add(pro);
            }
            System.out.println(intface+"服务使用者在注册中心"+socket.getInetAddress().getHostName()+":"+socket.getPort()+"注册成功");
        }catch (Exception e){
            System.out.println(intface+"服务使用者在注册中心"+socket.getRemoteSocketAddress()+":"+socket.getPort()+"注册失败，原因："+e.getMessage());
            e.printStackTrace();
        }
    }
}
