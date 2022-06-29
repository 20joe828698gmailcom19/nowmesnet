package com.newmesnet.nowmesnet.utils;

import com.newmesnet.nowmesnet.data.LoginCommand;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zqh
 * @create 2022-06-29 16:53
 */
public class CookieUtil {

    /**
     * cookie有效时间(秒)
     */
    public static final int COOKIE_MAX_AGE_30D = 30 * 24 * 60 * 60;
    public static final int COOKIE_MAX_AGE_7D = 7 * 24 * 60 * 60;
    public static final int COOKIE_MAX_AGE_3D = 3 * 24 * 60 * 60;
    public static final int COOKIE_MAX_AGE_3Y = 3 * 365 * 24 * 60 * 60;

    /**
     * 生成cookie
     * @param response
     * @param key
     * @param value
     * @param httpOnly
     */
    public static void generateCookie(HttpServletResponse response, String key, String value, boolean httpOnly){
        Cookie cookie = new Cookie(key,value);
        cookie.setMaxAge(COOKIE_MAX_AGE_3D);
        cookie.setHttpOnly(httpOnly);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 获取cookie信息
     * @param request
     * @param key
     * @return
     */
    public String getCookie(HttpServletRequest request, String key){

        return null;
    }
}
