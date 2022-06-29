package com.newmesnet.nowmesnet.service;

import com.newmesnet.nowmesnet.data.LoginCommand;
import com.newmesnet.nowmesnet.data.RegisterInfo;
import com.newmesnet.nowmesnet.entity.User;

/**
 * @author zqh
 * @create 2022-06-23 14:26
 */
public interface IUserService {
    User doLoginUser(LoginCommand loginCommand);

    User doRegisterUser(RegisterInfo registerInfo);
}
