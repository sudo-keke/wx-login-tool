package com.yq.wx.login;

import com.yq.wx.bean.WeixinOauth2Token;
import com.yq.wx.common.BaseResult;
import com.yq.wx.common.Constants;
import com.yq.wx.util.CommonUtil;
import com.yq.wx.util.WeiXinUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName :  PCLogin
 * @Author :  Yanqinag
 * @Date :  2019-07-31 22:48
 * @Description :  PC 扫码登录
 * <p>
 * 整体请求流程：
 * 登录页面点击微信登录 ——> 请求后端的qrconnect()接口
 * ——> 后端请求：https://open.weixin.qq.com/connect/qrconnect 并且传递用户扫码后微信回调项目的地址（callBack）
 * ——> 微信自己去判断用户有没有扫码(不需要自己写)
 * ——> (已扫码)微信回调（callBack）你请求时传递的接口
 * PS:
 * 以下为排坑！！！
 * 微信公众平台 and 微信开放平台 是不一样的东西
 * 微信登录分为 1·PC端, 2·APP, 3·小程序, 4·公众号 ....
 * 以上调用的地址是不同的！！！ PC端 在这个地址注册 https://open.weixin.qq.com/ 点网站应用开发，好像是注册一个三百多块钱，准备好money
 * 这个地址是公众号的开发！！！https://mp.weixin.qq.com/wiki 千万不要看这个去开发PC
 * 不同应用之间APPID和Secret都不一样(即使多个小程序或多个PC也是不同的)，一个应用一个，千万不要用错了，
 */
public class PCLogin {

    private static Logger log = LoggerFactory.getLogger(PCLogin.class);

    /**
     * @Author : Yanqiang
     * @Date : 2019-08-01
     * @Params : [request, response]
     * @Return : com.yq.wx.common.BaseResult
     * @Description : 返回微信二维码，可供扫描登录
     * <p>
     * 1.可以使用 response.sendRedirect(result); 重定向到微信返回的二维码页面，拉起二维码
     * 2.如果需要内嵌在自己的页面中，也可以返回给前端项目，让前端自行处理
     * <p>
     * 详细介绍链接：https://developers.weixin.qq.com/doc/oplatform/Website_App/WeChat_Login/Wechat_Login.html
     */
    public BaseResult qrconnect(HttpServletRequest request, HttpServletResponse response) {
        BaseResult baseResult = new BaseResult();
        String wxLoginurl = Constants.WX_QRCONNECT
                .replace("{APPID}", Constants.PCAppId)
                .replace("{REUTL}", Constants.callBack)
                .replace("{STATE}", CommonUtil.createRandom())
                .replace("{HREF}", Constants.callBackCss);
        String result = response.encodeURL(wxLoginurl);
        log.info("==扫码登录url,wxLoginurl :{} \n ==扫码返回 :{}", wxLoginurl, result);
        // response.sendRedirect(result);
        baseResult.setData(result);
        return baseResult;
    }

    /**
     * @return : java.lang.String
     * @Author : Yanqiang
     * @Date : 2019/3/7
     * @Param : [map, request, response]
     * @Description : 微信获取用户信息
     */
    public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String code = request.getParameter("code");

        // 通过code获取access_token
        WeixinOauth2Token oauth2Token = WeiXinUtil.getOauth2AccessToken(Constants.PCAppId, Constants.PCSecret, code);
        String accessToken = oauth2Token.getAccessToken();
        String openId = oauth2Token.getOpenId();

        //获取到用户的基本信息
        JSONObject snsUserInfo = WeiXinUtil.getSNSUserInfo(accessToken, openId);
        log.info("===========snsUserInfo = {}", snsUserInfo);
        if (snsUserInfo != null) {
            String unionId = (String) snsUserInfo.get("unionid");
            //通过unionid查询用户

            //获取完用户信息之后，重定向到项目的前端地址，
            response.sendRedirect(Constants.address + "/shops?code=1&userUnionId=" + unionId);
        } else {
            //获取不到用户 登录失败，code = 0
            log.info("==登录失败：" + Constants.address + "/shops?code=0");
            response.sendRedirect(Constants.address + "/shops?code=0");
        }
    }

}
