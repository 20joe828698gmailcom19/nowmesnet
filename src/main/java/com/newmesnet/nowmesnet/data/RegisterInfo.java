package com.newmesnet.nowmesnet.data;

import lombok.Data;

/**
 * @author zqh
 * @create 2022-06-24 16:42
 */
@Data
public class RegisterInfo {

    /**
     * user
     */
    private String userId;
    private String password;
    private String userEmail;
    private String userPhone;

    /**
     * user_info
     */
    private String userName;
    private String userCity;
    private String userSex;
    private String userBirthday;

}
