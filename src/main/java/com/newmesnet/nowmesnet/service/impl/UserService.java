package com.newmesnet.nowmesnet.service.impl;

import com.newmesnet.nowmesnet.Enum.UserState;
import com.newmesnet.nowmesnet.Enum.UserStatus;
import com.newmesnet.nowmesnet.data.LoginCommand;
import com.newmesnet.nowmesnet.data.RegisterInfo;
import com.newmesnet.nowmesnet.entity.LoginLog;
import com.newmesnet.nowmesnet.entity.User;
import com.newmesnet.nowmesnet.entity.UserInfo;
import com.newmesnet.nowmesnet.persistence.jpa.LoginLogRepository;
import com.newmesnet.nowmesnet.persistence.jpa.UserInfoRepository;
import com.newmesnet.nowmesnet.persistence.jpa.UserRepository;
import com.newmesnet.nowmesnet.service.IOAuthService;
import com.newmesnet.nowmesnet.service.IUserService;
import com.newmesnet.nowmesnet.utils.EncryptUtil;
import com.newmesnet.nowmesnet.utils.RegexUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zqh
 * @create 2022-06-23 14:28
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private LoginLogRepository loginLogRepository;

    @Autowired
    private IOAuthService oAuthService;

    private static EncryptUtil encryptUtil;



    /**
     * 传统的账号和密码方式登录
     * @param loginCommand
     * @return
     */
    @Override
    public User doLoginUser(LoginCommand loginCommand) {

        String userId = loginCommand.getUsername();
        String userEmail = loginCommand.getUsername();
        String userPhone = loginCommand.getUsername();
        String userPassword = loginCommand.getPassword();

        User user = new User();
        LoginLog loginLog = new LoginLog();

        boolean mobiles = RegexUtil.isMobiles(userPhone);
        boolean email = RegexUtil.isEmail(userEmail);
        if(StringUtils.isNotBlank(userPhone) || mobiles){
            user = userRepository.findByUserPhone(userPhone);
        }else if(StringUtils.isNotBlank(userEmail) || email){
            user = userRepository.findByUserEmail(userEmail);
        }else if(StringUtils.isNotBlank(userId)){
            user = userRepository.findByUserId(userId);
        }

        if(user != null){
            boolean checkBCrpt = encryptUtil.checkBCrpt(userPassword, user.getUserPassword());
            if(!checkBCrpt){
                //后续需要进行更一步反馈信息为登录的账号或密码错误
                loginLog.setUserId(userId);
                loginLog.setLoginType("账号密码登陆");
                loginLog.setLoginFlag("密码错误");
                loginLog.setLoginTime(new Date());
                loginLog.setLoginCity(null);
                loginLog.setLoginDevice(null);
                loginLog.setLoginIp(null);
                loginLogRepository.saveAndFlush(loginLog);

                //登录失败，向redis中登录失败次数+1
                if(mobiles){
                    oAuthService.addLoginRequestFailByUserId(userPhone,true);
                }else if(email){
                    oAuthService.addLoginRequestFailByUserId(userEmail,true);
                }else {
                    oAuthService.addLoginRequestFailByUserId(userId,true);
                }
                return null;
            }else {
                if(UserStatus.Disable.equals(user.getUserStatus())){
                    //反馈为账号已被封禁。

                    loginLog.setUserId(userId);
                    loginLog.setLoginType("账号密码登陆");
                    loginLog.setLoginFlag("登录被封禁的账号");
                    loginLog.setLoginTime(new Date());
                    loginLog.setLoginCity(null);
                    loginLog.setLoginDevice(null);
                    loginLog.setLoginIp(null);
                    loginLogRepository.saveAndFlush(loginLog);

                    return null;
                }
                user.setUserIp(null);
                user.setUserDevice(null);
                user.setUserLastLoginDateTime(new Date());
                user.setUserState(UserState.Online);
                userRepository.saveAndFlush(user);

                loginLog.setUserId(userId);
                loginLog.setLoginType("账号密码登陆");
                loginLog.setLoginFlag("登录成功");
                loginLog.setLoginTime(new Date());
                loginLog.setLoginCity(null);
                loginLog.setLoginDevice(null);
                loginLog.setLoginIp(null);
                loginLogRepository.saveAndFlush(loginLog);
                return user;
            }
        }
        loginLog.setUserId(userId);
        loginLog.setLoginType("账号密码登陆");
        loginLog.setLoginFlag("账号错误");
        loginLog.setLoginTime(new Date());
        loginLog.setLoginCity(null);
        loginLog.setLoginDevice(null);
        loginLog.setLoginIp(null);
        loginLogRepository.saveAndFlush(loginLog);

        return user;
    }

    @Override
    public User doRegisterUser(RegisterInfo registerInfo) {
        if(StringUtils.isBlank(registerInfo.getPassword()) || !RegexUtil.checkPassword(registerInfo.getPassword())){
            return null;
        }


        if(StringUtils.isNotBlank(registerInfo.getUserId())){
            User byUserId = userRepository.findByUserId(registerInfo.getUserId());
            //校验userId是否存在，如果存在就不能注册成功，直接返回null
            if(registerInfo.getUserId().equals(byUserId.getUserId())){
                return null;
            }
        }
        if(StringUtils.isNotBlank(registerInfo.getUserEmail())){
            //邮件需要唯一性，不然没法使用邮箱登录
            User byUserEmail = userRepository.findByUserEmail(registerInfo.getUserEmail());
            //校验userId是否存在，如果存在就不能注册成功，直接返回null
            if(registerInfo.getUserEmail().equals(byUserEmail.getUserEmail())){
                return null;
            }
        }
        if(StringUtils.isNotBlank(registerInfo.getUserPhone())){
            //手机号需要唯一性，不然没法使用手机号登录
            User byUserPhone = userRepository.findByUserPhone(registerInfo.getUserPhone());
            //校验userId是否存在，如果存在就不能注册成功，直接返回null
            if(registerInfo.getUserPhone().equals(byUserPhone.getUserPhone())){
                return null;
            }
        }

        UserInfo userInfo = new UserInfo();
        User user = new User();

        user.setUserId(registerInfo.getUserId());
        user.setUserEmail(registerInfo.getUserEmail());
        user.setUserPhone(registerInfo.getUserPhone());
        user.setUserState(UserState.Logout);
        user.setUserStatus(UserStatus.Enable);
        user.setUserCreateDateTime(new Date());
        String password = registerInfo.getPassword();
        //对密码进行加密。后期可以优化，再采取一遍对称加密方式，加密一遍。在登录的时候，对数据库的密码进行一遍解密再checkBCrpt。这样安全性会相对高些
        String bCryptEncrypt = encryptUtil.bCryptEncrypt(password);
        user.setUserPassword(bCryptEncrypt);
        userRepository.saveAndFlush(user);

        userInfo.setUserBirthday(registerInfo.getUserBirthday());
        userInfo.setUserCity(registerInfo.getUserCity());
        userInfo.setUserId(registerInfo.getUserId());
        userInfo.setUserSex(registerInfo.getUserSex());
        userInfoRepository.saveAndFlush(userInfo);

        return user;
    }



}
