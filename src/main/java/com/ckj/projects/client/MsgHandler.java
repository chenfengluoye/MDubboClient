package com.ckj.projects.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * created by ChenKaiJu on 2018/8/4  11:31
 */
public class MsgHandler extends Thread{

    Socket socket;

    public MsgHandler(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream=socket.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
