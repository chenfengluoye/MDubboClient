package com.ckj.projects.utils;

import com.alibaba.fastjson.JSON;
import com.ckj.projects.client.RequestMethod;
import com.ckj.projects.client.ResponseMethod;

import java.io.*;

/**
 * created by ChenKaiJu on 2018/8/27  21:34
 */
public class ObjectAndByte {


    public static byte[] jdkToBytes (Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    public static Object jdKToObject (byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
            ObjectInputStream ois = new ObjectInputStream (bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }


//    public static byte[] msgPackToBytes(Object o){
//
//        JSFMsgPack msgPack=new JSFMsgPack();
//        try {
//            byte[] bytes=msgPack.write(o);
//            return bytes;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static <T> T msgPackToObj(byte[]bytes,Class<T> tClass){
//        JSFMsgPack msgPack=new JSFMsgPack();
//        try {
//            return (T)msgPack.read(bytes,tClass);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static byte[] fjsonToBytes(Object obj){
        byte[] bytes=null;
        try{
            bytes=JSON.toJSONBytes(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bytes;
    }

    public static <T>T fjsonToObj(byte[]bytes,Class<T> tClass){
        T obj=null;
        try{
            obj=JSON.parseObject(bytes,tClass);
        }catch (Exception e){
            e.printStackTrace();
        }
        return obj;
    }

}
