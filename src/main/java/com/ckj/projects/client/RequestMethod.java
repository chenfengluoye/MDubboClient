package com.ckj.projects.client;

import java.io.Serializable;
import java.util.Arrays;

/**
 * created by ChenKaiJu on 2018/8/12  16:49
 */
public class RequestMethod implements Serializable {

    private static final long serialVersionUID = 1004313235820466440L;

    private String msgReq;

    private String methodName;

    private Class[] paramTypes;

    private Class  methodClass;

    private Object[] params;

    private Class returnType;

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Class getMethodClass() {
        return methodClass;
    }

    public void setMethodClass(Class methodClass) {
        this.methodClass = methodClass;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMsgReq() {
        return msgReq;
    }

    public void setMsgReq(String msgReq) {
        this.msgReq = msgReq;
    }

}
