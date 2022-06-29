package com.newmesnet.nowmesnet.exception;

import lombok.Getter;

/**
 * @author zqh
 * @create 2022-06-23 14:56
 */
@Getter
public enum ErrorDefinition {
    USER_IS_NOT_EXIST("API_0001","User is not exist."),
    USER_LOGIN_FAIL("API_0002","User login failed, possibly wrong password or username."),
    USER_INFO_NOT_NULL("API_0003","User info does not null."),
    USER_REGISTER_INFO_NOT_NULL("API_0004","User register info does not null."),
    USER_REGISTER_FAIL("API_0005","User register failed, userId already exists."),
    USER_REGISTER_FAIL_USERID_ERROR("API_0006","User register failed, the length of userId must be between 6-16 digits."),
    USER_LOGIN_FAIL_TOO_MANY("API_0007","User login failed, too many login failures"),
    USER_REGISTER_FAIL_TOO_MANY("API_0007","User register failed, too many register failures"),


    ;

    private String errorCode;

    private String errorDescription;

    ErrorDefinition(String errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
}

