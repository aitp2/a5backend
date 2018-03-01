package com.wechat.backend.service.util;

import net.sf.json.JSONObject;

public class Test {
    private static final String OAUTH_STRING = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

  /*  public static void main(String[] args){
        String requestUrl = OAUTH_STRING.replace("APPID", "wx83e14f0011b6c046")
            .replace("SECRET", "f863bf38bf3f438af82128c05fd320b1").replace("JSCODE", "021zYKEi2cPlvI0MUwFi2EfLEi2zYKEq");
        JSONObject jsonObject = HttpsRequestUtil.httpsRequest(requestUrl, "GET", null);
        System.out.println(jsonObject);
    }*/
}
