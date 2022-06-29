package com.newmesnet.nowmesnet.utils;


import org.springframework.util.DigestUtils;


/**
 * 加密工具类
 * @author zqh
 * @create 2022-06-23 16:18
 */
public class EncryptUtil {

    /**
     * Md5加盐加密法，正常来说不可逆
     * @param text 明文
     * @param salt  盐
     * @return
     */
    public String md5Encrypt(String text, String salt){
        String ciphertext = "";//密文结果
        String tmp = text + salt;
        ciphertext = DigestUtils.md5DigestAsHex(tmp.getBytes());
        return ciphertext;
    }

    /**
     * 对称加密
     * @param text  原文
     * @param secretKey  秘钥
     * @return
     */
    public String aesEncrypt(String text, String secretKey){
        String ciphertext = "";//密文结果


        return ciphertext;
    }


    /**
     * 正常来说不可逆，非对称加密
     * @param text
     * @return
     */
    public String bCryptEncrypt(String text){
        String ciphertext = "";//密文结果

        String gensalt = BCrypt.gensalt();//这个是盐  29个字符，随机生成
        ciphertext = BCrypt.hashpw(text, gensalt);  //根据盐对密码进行加密

        return ciphertext;
    }


    public boolean checkBCrpt(String text, String ciphertext){

        boolean ans = BCrypt.checkpw(text, ciphertext);//正确返回 true

        return ans;

    }

}
