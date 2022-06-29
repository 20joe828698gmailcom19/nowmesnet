package com.newmesnet.nowmesnet.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author zqh
 * @create 2022-06-29 10:48
 */
public class RegexUtil {

    /**
     * 判断手机格式是否正确
     */
    private static String check ="((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))";

    public static boolean isMobiles(String tel) {
        if (StringUtils.isEmpty(tel)) {
            return false;
        }
        return Pattern.matches(check, tel);
    }

    /**
     * 判断email格式是否正确
     */
    public static boolean isEmail(String email) {
        return email.matches("^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    }

    /**
     * 密码强度正则，最少6位
     */
    public static boolean checkPassword(String content){
        if(content==null) return false;
        //String regex= "^.*(?=.{6,})(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$"; 包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符
        String regex= "^[0-9A-Za-z]{6,12}$";
        return Pattern.matches(regex, content);
    }


}
