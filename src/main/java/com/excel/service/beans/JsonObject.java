package com.excel.service.beans;

public final class JsonObject {
    /**
     * 响应码
     **/
    private String code = "0";

    /**
     * 响应消息
     **/
    private String msg;

    /**
     * 响应数据
     **/
    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
