package com.yq.wx.util;


import com.yq.wx.bean.AccessToken;
import com.yq.wx.bean.WeixinOauth2Token;
import com.yq.wx.common.Constants;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author : Yanqiang
 * @Date : 2019/3/22
 * @Description :
 * <p>
 * Copyright ©2017-2019 Beijing HeXinHuiTong Co.,Ltd
 * All Rights Reserved.
 * 2017-2019 北京和信汇通科技开发有限公司 版权所有
 */
public class WeiXinUtil {

    private static Logger log = LoggerFactory.getLogger(WeiXinUtil.class);

    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public final static String get_userInfo_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    public final static String get_hangye_url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";

    //上面那五个，不用改。把passPortProperties里面的appid和appsecret改了就行

    /**
     * @return : com.mjt.passport.util.WeiXinUtils.WeixinOauth2Token
     * @Author : Yanqiang
     * @Date : 2019/3/7
     * @Param : [appId, appSecret, code]
     * @Description : 网页授权认证
     */
    public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        //路径替换参数
        String requestUrl = Constants.oauth2_access_url
                .replace("APPID", appId)
                .replace("SECRET", appSecret)
                .replace("CODE", code);
        //发送请求获取网页授权凭证，获取 token
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.GET, null);
        WeixinOauth2Token wxo = new WeixinOauth2Token();
        wxo.setAccessToken(jsonObject.getString("access_token"));
        wxo.setExpiresIn(jsonObject.getInt("expires_in"));
        wxo.setRefreshToken(jsonObject.getString("refresh_token"));
        wxo.setOpenId(jsonObject.getString("openid"));
        wxo.setScope(jsonObject.getString("scope"));
        return wxo;
    }


    /**
     * @return : com.mjt.passport.util.WeiXinUtils.SNSUserInfo
     * @Author : Yanqiang
     * @Date : 2019/3/7
     * @Param : [accessToken, openId]
     * @Description : 获取用户的基本信息 打印log日志 便于查找问题
     *  通过openid 和 accessToken 获取用户信息
     */
    public static JSONObject getSNSUserInfo(String accessToken, String openId) {
        String requestUrl = Constants.sns_userinfo
                .replace("ACCESS_TOKEN", accessToken)
                .replace("OPENID", openId);
        log.info("===========requestUrl = " + requestUrl + "===========");
        //通过网页授权获取用户信息
        return CommonUtil.httpsRequest(requestUrl, Constants.GET, null);
    }




    /**
     * @return : com.mjt.passport.util.WeiXinUtils.AccessToken
     * @Author : Yanqiang
     * @Date : 2019/3/7
     * @Param : [appid, appSecret]
     * @Description : AccessToken
     */
    public static AccessToken getAccessToken(String appid, String appSecret) {
        //替换真实 appid 和 appsecret
        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appSecret);
        AccessToken accesstoken = new AccessToken();
        //得到json对象
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        //将得到的json对象的属性值，存到accesstoken中
        accesstoken.setToken(jsonObject.getString("access_token"));
        accesstoken.setExpiresIn(jsonObject.getInt("expires_in"));
        return accesstoken;
    }




    /**
     * @return : com.mjt.passport.util.WeiXinUtils.WeixinOauth2Token
     * @Author : Yanqiang
     * @Date : 2019/3/7
     * @Param : [appId, appSecret, code]
     * @Description : 网页授权认证
     */
   /* public JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);

        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.fromObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    *//**
     * @return : java.lang.String
     * @Author : Yanqiang
     * @Date : 2019/3/7
     * @Param : [redirectUri]
     * @Description : 创建网页授权的url
     *//*
    public static String createUrl(String redirectUri) {

        String url = get_userInfo_url.replace("APPID", passPortProperties.pcWeixinAppid).replace("REDIRECT_URI", CommonUtil.urlEncodeUTF8(redirectUri)).replace("SCOPE", "snsapi_userinfo");
        System.out.println(url);
        return url;
    }

    *//**
     * @return : java.lang.String
     * @Author : Yanqiang
     * @Date : 2019/3/7
     * @Param : [longURL, wxAppId, secret]
     * @Description : 长连接转化成短链接，提高扫码速度跟成功率
     *//*
    public static String shortURL(String longURL, String wxAppId, String secret) {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
        try {
            //将更新后的access_token,替换上去
            requestUrl = requestUrl.replace("ACCESS_TOKEN", getAccessToken(wxAppId, secret).getToken());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String jsonMsg = "{\"action\":\"long2short\",\"long_url\":\"%s\"}";
        //格式化url
        String.format(jsonMsg, longURL);
        JSONObject jsonobject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, longURL));
        log.info("=====getUserInfo返回jsonobject为: " + jsonobject + "======================");
        //转换成短连接成功
        return jsonobject.getString("short_url");
    }*/

/*    public static void main(String[] args) {
        String en = "75loZ3UrIZVGjIcTNxiWpptbXcvtNHouIRT6uSuyEuDBwVYqAixP4WsE/mWMjF+jZTf2HRRb5SWkUbyOtcBc9yz1CaMV+uATN+ZZeDwhT1E7DIyJteyaSZFK3jDeepLPEWbpRGRVMEsqvLRJraclRAxAA13nxCleuD+yoHKKVg4DqeMWH7tgIqEo7j3/ybGH/RcEfFmBVBXwtEnLGuNHWNPENq88UUeE5oTS/sqJnYxd+8KzC99ie5+9bHt5cCpqVTt9ToREIzJOy880m5FEkVGHCDIcQwxXQ5MErQHHdgf4YMN+039hDRJaBPByA1uynMH7Bl5NxPJ+xI3/8gtV+Z4Mpf54N9cjsjhGp21pgeeFa5qJ//NqgY1WgeULsBFfPmdqVnp+Izkmyx08F+QusvT0tmlKkU+NkpTkwdkUGa5gBDmfxSt72WNuk6vcl5WFssXx5dVqaM7N2VLh6OJVMFN5kxWOCkLwe+eao08w4dEwTTGBYMoVovChtfFXIPe1XEbwTpyO5vi3qRfNiEHHeg==";
        String iv = "XRMT2G6kQaQiEOipT9QLiw==";
        String s = "fQPod43Z8nfJQ/2ay8cTsQ==";
        JSONObject userInfo = getUserInfo(en, s, iv);
        System.out.println(userInfo);
    }*/

}
