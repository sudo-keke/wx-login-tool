package com.yq.wx.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @Author : Yanqiang
 * @Date : 2019-07-31
 * @Description : 统一返回结果
 */
public class BaseResult implements Serializable {

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 状态码：1成功，其他为失败
     */
    private String code = "1";

    /**
     * 成功为success，其他为失败原因
     */
    private String message = "success";

    /**
     * token
     */
    private String token;

    /**
     * 数据结果集
     */
    private Object data;


    /**
     * 带参构造
     */
    public BaseResult(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 无参构造
     */
    public BaseResult() {

    }

    public String getCode() {
        return code;
    }

    private void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
        if (!code.equals("1")) {
            logger.error(message);
        }
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                ", data=" + data +
                '}';
    }


}
