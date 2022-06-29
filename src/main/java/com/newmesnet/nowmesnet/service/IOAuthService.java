package com.newmesnet.nowmesnet.service;

/**
 * @author zqh
 * @create 2022-06-29 14:11
 */
public interface IOAuthService {

    Integer getLoginRequestFailByUserId(String userId);

    void addLoginRequestFailByUserId(String userId, boolean flag);

    void addLoginTokenByUserId(String userId, String token);

    void clearLoginTokenByUserId(String userId, String token);

    Integer getRegisterRequestByIp(String userIp);

    void addRegisterRequestByIp(String userIp, boolean flag);

    void clearLoginRequestByUserId(String userId);

    void clearRegisterRequestByIp(String userIp);

}
