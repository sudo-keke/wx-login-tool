package com.yq.wx.util;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.yq.wx.bean.WeixinOauth2Token;
import com.yq.wx.common.Constants;
import net.sf.json.JSONObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

/**
 * @Author : Yanqiang
 * @Date : 2019-08-01
 * @Description : 微信登录工具类
 */
public class WeiXinUtil {

    private static Logger log = LoggerFactory.getLogger(WeiXinUtil.class);

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
        //通过网页授权获取用户信息，本来想封装成一个 Javabean 对象的，后来一想不如返回一个 jsonobject 更加方便
        return CommonUtil.httpsRequest(requestUrl, Constants.GET, null);
    }




    /**
     * @return : com.mjt.passport.util.WeiXinUtils.WeixinOauth2Token
     * @Author : Yanqiang
     * @Date : 2019/3/7
     * @Param : [appId, appSecret, code]
     * @Description : 微信用户信息解密使用
     */
    public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
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


}
