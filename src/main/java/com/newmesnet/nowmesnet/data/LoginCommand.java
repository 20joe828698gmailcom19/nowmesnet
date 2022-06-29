package com.newmesnet.nowmesnet.data;

import lombok.Data;

/**
 * @author zqh
 * @create 2022-06-23 14:54
 */
@Data
public class LoginCommand {
    private String username;
    private String password;
    private Boolean memberpass = false;
    private String codeId;
    private String codeValue;

}
