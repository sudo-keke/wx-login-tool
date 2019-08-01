package com.yq.wx.common;

/**
 * @Author : Yanqiang
 * @Date : 2019-07-31
 * @Description : 返回状态码
 */
public enum StatusCode {

    NONE_ERROR("1", "成功"),
    /**
     * 1 系统内部错误
     */
    SYSTEM_ERROR("JLD_10000", "系统内部异常"),
    PARAM_ERROR("JLD_10001", "参数异常"),
    PARAM_JSON_ERROR("JLD_10003", "参数非json格式"),


    /**
     * 2 开头（外部资源使用异常）
     */
    SQL_ERROR("JLD_21000", "数据库操作异常"),
    SQL_LINK_ERROR("JLD_21001", "数据库链接异常"),
    REDIS_ERROR("JLD_21002", "redis异常"),
    REDIS_LINK_ERROR("JLD_21003", "redis链接异常"),

    /**
     * 3 开头 登录相关错误
     */
    PWD_COUNT_ERROR("JLD_30000", "您的密码已连续5次输入错误,请24小时后重试"),
    USER_LOGIN_FAIL_ERROR("JLD_30001", "登录失败，请稍后重试"),
    USER_LOGIN_FIRST_JLD_STORE("JLD_30002", "请先登录集乐多商城"),
    SYSTEM_BUSY_ERROR("JLD_30003", "系统繁忙，请开发者稍候再试"),
    LOGIN_CODE_ERROR("JLD_30004", "code 无效"),
    FREQUENCY_OUT_ERROR("JLD_30005", "您已超出频率限制次数，请稍后再试"),
    WX_DECRYPT_ERROR("JLD_30006", "解密用户信息失败"),

    /**
     * 4 开头 业务执行错误
     */
    UPDATE_USER_ERROR("JLD_40001", "更新用户信息失败，请重试"),
    VERIFICATION_KEY_ERROR("JLD_40002", "您的私钥key有误"),
    TOKEN_PAST_DUE("JLD_40003", "根据当前token无法获取到用户"),
    DELICER_TALEORDER_ERROR("JLD_40004", "批量发货出错，请重试"),
    BATCH_RECEIVING("JLD_40005", "批量接单出错，请重试"),

    END_ERROR("JLD_50001", "订单当前状态不可关闭");



    private String code;
    private String msg;

    StatusCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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

}
