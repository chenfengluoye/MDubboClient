package com.ckj.projects.client;

public class CallBackService {

    public Object retObj;

    public void Call(Object retObj){
        this.retObj=retObj;
    }


    public Object getRetObj() {
        return retObj;
    }
}
