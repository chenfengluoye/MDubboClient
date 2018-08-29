package com.ckj.projects.client;


import java.io.Serializable;

/**
 * created by ChenKaiJu on 2018/8/12  16:57
 */
public class ResponseMethod implements Serializable {

    private static final long serialVersionUID = 1004313235820466440L;

    Object respObj;

    String msgReq;

    public Object getRespObj() {
        return respObj;
    }

    public void setRespObj(Object respObj) {
        this.respObj = respObj;
    }


    public String getMsgReq() {
        return msgReq;
    }

    public void setMsgReq(String msgReq) {
        this.msgReq = msgReq;
    }
}
