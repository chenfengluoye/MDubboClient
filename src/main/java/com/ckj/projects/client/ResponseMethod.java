package com.ckj.projects.client;

import java.io.Serializable;

/**
 * created by ChenKaiJu on 2018/8/12  16:57
 */
public class ResponseMethod implements Serializable {


    Object respObj;



    public Object getRespObj() {
        return respObj;
    }

    public void setRespObj(Object respObj) {
        this.respObj = respObj;
    }
}
