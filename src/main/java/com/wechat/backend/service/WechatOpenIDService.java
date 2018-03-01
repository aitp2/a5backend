package com.wechat.backend.service;

import com.wechat.backend.config.ApplicationProperties;
import com.wechat.backend.service.util.HttpsRequestUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WechatOpenIDService {
    @Autowired
    private ApplicationProperties applicationProperties;
    public String getOpenId(String jsCode) {
        String openId = null;
        if (StringUtils.isNoneBlank(jsCode)) {
            String requestUrl = applicationProperties.getWx().getOauthUri().replace("APPID", applicationProperties.getWx().getAppId())
                .replace("SECRET", applicationProperties.getWx().getAppSecret()).replace("JSCODE", jsCode);
            JSONObject jsonObject = HttpsRequestUtil.httpsRequest(requestUrl, "GET", null);
            openId = (jsonObject != null)&&jsonObject.containsKey("openid") ? jsonObject.getString("openid") : StringUtils.EMPTY;
        }
        return openId;
    }
}
