package com.yq.wx.common;

/**
 * @Author : Yanqiang
 * @Date : 2019/3/22
 * @Description : 动态常量
 */
public final class Constants {

    /**
     * 不可实例化对象
     */
    private Constants() {
        throw new UnsupportedOperationException();
    }


    /**
     * appid
     */
    public static final String GET = "GET";
    /**
     * appid
     */
    public static final String POST = "POST";

    //=========================================微信应用配置=========================================

    //===================PC======================
    /**
     * appid
     */
    public static final String PCAppId = "xxxxx";

    /**
     * secret
     */
    public static final String PCSecret = "xxxxx";

    //====================小程序 1=====================
    /**
     * appid
     */
    public static final String MP1Appid = "xxxxx";

    /**
     * secret
     */
    public static final String MP1Secret = "xxxxx";

    //=====================小程序 2====================
    /**
     * appid
     */
    public static final String MP2Appid = "xxxxx";

    /**
     * secret
     */
    public static final String MP2Secret = "xxxxx";


    //=========================================PC 登录=========================================

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
     * 调起微信登录二维码
     * 介绍一下几个参数的含义：
     *     appid：           该应用的 APPID
     *     redirect_uri：    回调你自己项目的 getUserInfo 接口对外地址
     *     state：           随机数，回调 redirect_uri 使用
     *     href：            自己加的参数，调用自己的 css
     */
    public static final String WX_QRCONNECT = "https://open.weixin.qq.com/connect/qrconnect?appid={APPID}&redirect_uri={REUTL}&response_type=code&scope=snsapi_login&state={STATE}&href={HREF}#wechat_redirect";


    /**
     * 微信通过 code 获取 access_token
     *      appid:      该应用的 appid
     *      secret:     该应用的 secret
     *      code:       拉起二维码的时候创建的随机code
     */
    public final static String oauth2_access_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    

    /**
     * 获取用户信息
     */
    public final static String sns_userinfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";



    //=========================================小程序登录=========================================
    /**
     * 小程序登录
     */
    public final static String sns_jscode2session = "https://api.weixin.qq.com/sns/jscode2session?appid={APPID}&secret={SECRET}&js_code={CODE}&grant_type=authorization_code";


}
