package com.ckj.projects.client;

public class CallBackService {

    boolean isTimeOut=true;

    public Object retObj;

    public void Call(Object retObj){
        synchronized (this){
            this.retObj=retObj;
            this.isTimeOut=false;
            this.notify();
        }
    }

    public Object getRetObj() {
        return retObj;
    }

    public boolean isTimeOut() {
        return isTimeOut;
    }

}
