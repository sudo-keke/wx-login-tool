package com.yq.wx.common;

/**
 * @Author : Yanqiang
 * @Date : 2019/3/22
 * @Description : 动态常量
 */
public final class Constants {

    /**
     * appid
     */
    public static final String GET = "GET";
    /**
     * appid
     */
    public static final String POST = "POST";

    public static final String APPLICATION_CHARACTER_ENCODING = "UTF-8";
    public static final String UTIL_ENCRYPT_PASSWORD_ALGORITHM = "PBKDF2WithHmacSHA1";
    //盐值5位，生成后为10位
    public static final int UTIL_ENCRYPT_PASSWORD_SALT_BYTE_SIZE = 5;
    public static final int UTIL_ENCRYPT_PASSWORD_HASH_BYTE_SIZE = 32;
    public static final int UTIL_ENCRYPT_PASSWORD_ALGORITHM_ITERATIONS = 1000;


    /**
     * 调起微信登录二维码
     * 介绍一下几个参数的含义：
     *     appid：           该应用的 APPID
     *     redirect_uri：    回调你自己项目的 getUserInfo 接口对外地址
     *     state：           随机数，回调 redirect_uri 使用
     *     href：            自己加的参数，调用自己的 css
     */
    public static final String WX_QRCONNECT = "https://open.weixin.qq.com/connect/qrconnect?appid={APPID}&redirect_uri={REUTL}&response_type=code&scope=snsapi_login&state={STATE}&href={HREF}#wechat_redirect";

    /**
     * appid
     */
    public static final String PCAppId = "xxxxx";

    /**
     * secret
     */
    public static final String PCSecret = "xxxxx";

    /**
     * 微信回调，自己项目的外网地址
     */
    public static final String address = "www.baidu.com";

    /**
     * 微信回调，自己项目的外网地址
     */
    public static final String callBack = "www.baidu.com/pc/login/getUserInfo";

    /**
     * css 样式
     */
    public static final String callBackCss = "www.baidu.com/pc/login/getUserInfo/callBack.css";

    /**
     * 微信通过 code 获取 access_token
     *      appid:      该应用的 appid
     *      secret:     该应用的 secret
     *      code:       拉起二维码的时候创建的随机code
     */
    public final static String oauth2_access_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    
    /**
     * @Author : Yanqiang
     * @Date : 2019-08-01
     * @Params : 
     * @Return : 
     * @Description : 
     */
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 获取用户信息
     */
    public final static String sns_userinfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";


    /**
     * 不可实例化对象
     */
    private Constants() {
        throw new UnsupportedOperationException();
    }

}
