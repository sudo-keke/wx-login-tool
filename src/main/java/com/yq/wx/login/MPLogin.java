package com.yq.wx.login;

import com.yq.wx.common.BaseResult;
import com.yq.wx.common.Constants;
import com.yq.wx.util.CommonUtil;
import com.yq.wx.util.WeiXinUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName :  MPLogin
 * @Author :  Yanqinag
 * @Date :  2019-08-01 13:05
 * @Description :  小程序登录
 *
 * PS : 是统一登录，包含多个小程序，以 source 区分来源
 */
public class MPLogin {

    private static Logger log = LoggerFactory.getLogger(MPLogin.class);


    /**
     * @Author : Yanqiang
     * @Date : 2019-07-22
     * @Params : [param]
     * @Return : com.jld.passport.common.BaseResult
     * @Description : 小程序端登录
     *
     * 微信登录小程序传参:
     * code :   类似 PC 登录的 code
     * fullUserInfo：  比较全的用户信息，里面还包了一层叫 userInfo，咱们就是为了取出里面这一层
     * source：  如果多个小程序，以 source 区分来源，单一小程序登录可以不需要，
     */
    @Transactional
    public BaseResult login(String code, String fullUserInfo, int source) {
        BaseResult baseResult = new BaseResult();
        //全部的用户信息
        JSONObject jsonFullUserInfo = JSONObject.fromObject(fullUserInfo);
        //userInfo信息，从这里面拿到用户信息，这个信息在下面的业务中会用到
        JSONObject userInfo = (JSONObject) jsonFullUserInfo.get("userInfo");
        String appid;
        String secret;
        //区分不同小程序的appid
        if (source == 1) {
            //小程序 1
            appid = Constants.MP1Appid;
            secret = Constants.MP1Secret;
        } else if (source == 2) {
            //小程序 2
            appid = Constants.MP2Appid;
            secret = Constants.MP2Secret;
        } else {
            return baseResult;
        }
        String loginUrl = Constants.sns_jscode2session.replace("{APPID}", appid)
                .replace("{SECRET}", secret)
                .replace("{CODE}", code);

        JSONObject jsonData = CommonUtil.httpsRequest(loginUrl, Constants.GET, null);
        String wxUnionId = (String) jsonData.get("unionid");
        //PS:没有关注公众号的用户是没有unionid的！！！
        if (wxUnionId == null) {
            //只能通过 encryptedData，iv，session_key，三个参数，去解密用户信息
            String encryptedData = (String) jsonFullUserInfo.get("encryptedData");
            String iv = (String) jsonFullUserInfo.get("iv");
            String sessionKey = (String) jsonData.get("session_key");
            //解密用户信息，拿到 unionId
            JSONObject jsonEncryptedData = WeiXinUtil.getUserInfo(encryptedData, sessionKey, iv);
            if (jsonEncryptedData == null) {
                return baseResult;
            }
            wxUnionId = (String) jsonEncryptedData.get("unionId");
        }
        if (wxUnionId != null) {
            //请求成功，通过unionid查询用户

            //执行登录记录
        } else {
            //错误
        }
        return baseResult;
    }
}
