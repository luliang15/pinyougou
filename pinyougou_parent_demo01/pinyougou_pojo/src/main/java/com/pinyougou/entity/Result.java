package com.pinyougou.entity;

import java.io.Serializable;

/**
 * @program:PinYouGou01
 * @description:操作结果封装集
 * @author:Mr.lu
 * @create:2019-07-10 17:01
 **/
public class Result implements Serializable {
    //是否成功
    private boolean success;
    //操作返回消息
    private String message;
    public Result(boolean success, String message) {
        super();
        this.success = success;
        this.message = message;
    }
    //getter and setter.....

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}

