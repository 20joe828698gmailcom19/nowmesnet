package com.newmesnet.nowmesnet.controller;

import com.newmesnet.nowmesnet.base.AbstractController;
import com.newmesnet.nowmesnet.base.ApiResponse;
import com.newmesnet.nowmesnet.data.LoginCommand;
import com.newmesnet.nowmesnet.data.RegisterInfo;
import com.newmesnet.nowmesnet.entity.User;
import com.newmesnet.nowmesnet.exception.ErrorDefinition;
import com.newmesnet.nowmesnet.service.IOAuthService;
import com.newmesnet.nowmesnet.service.IUserService;
import com.newmesnet.nowmesnet.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * @author zqh
 * @create 2022-06-23 14:53
 */
@RestController
public class UserController extends AbstractController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IOAuthService oAuthService;

    /**
     * 登录或注册失败重试次数上限
     */
    private static final int FAILED_RETRY_TIMES = 5;

    /**
     * 传统方式：账号和密码进行登录
     * @param request
     * @param response
     * @param loginCommand
     * @return
     */
    @RequestMapping(value = "/v1/nowmesnet/login", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> doLoginUser(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginCommand loginCommand){
        if(loginCommand == null){
            return createResponseEntity(ErrorDefinition.USER_INFO_NOT_NULL);
        }

        //需要做redis存储登录次数，防止刷请求登录接口，根据用户名来进行保存和累计次数。初步打算为10分钟内连续登陆超过5次就不再允许登录。要等1小时后才能进行登录。被封禁的账号，无法登陆

        Integer loginFailByUserId = oAuthService.getLoginRequestFailByUserId(loginCommand.getUsername());

        if(loginFailByUserId > FAILED_RETRY_TIMES){
            return createResponseEntity(ErrorDefinition.USER_LOGIN_FAIL_TOO_MANY);
        }
        //一小时后再进行登录，需要实现


        //后续为了安全可能还需要进行IP封禁之类的问题：同一IP或者同一网段的IP在10分钟内请求超过200次，将拒绝服务


        User user = userService.doLoginUser(loginCommand);

        if(user == null){
            return createResponseEntity(ErrorDefinition.USER_LOGIN_FAIL);
        }

        //需要进行cookie和session的创建和设置，并且需要把这些东西都返回到前端去，并且生成token
        //在cookie中设置一个session或token。同时这个值同步保存到redis中去。默认设置为3天有效期。可以前端勾选7天有效期。
        // 在后续的与账户有关的请求中校验此token与redis中是否一致。其中任何一方没有就需要重新登录
        String value = user.getUserId() + UUID.randomUUID().toString();
        CookieUtil.generateCookie(response, "SESSIONID",value,true);
        CookieUtil.generateCookie(response, "USERID",user.getUserId(),true);

        oAuthService.addLoginTokenByUserId(user.getUserId(), value);
        //注册成功清除redis中存储的登录错误信息
        oAuthService.clearLoginRequestByUserId(loginCommand.getUsername());

        return createResponseEntity();
    }



    /**
     * 注册页面提交注册
     * @param request
     * @param response
     * @param registerInfo
     * @return
     */
    @RequestMapping(value = "/v1/nowmesnet/register", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> doRegisterUser(HttpServletRequest request, HttpServletResponse response, @RequestBody RegisterInfo registerInfo){
        if(registerInfo == null){
            return createResponseEntity(ErrorDefinition.USER_REGISTER_INFO_NOT_NULL);
        }
        if(registerInfo.getUserId().length()>16 || registerInfo.getUserId().length()<6){
            return createResponseEntity(ErrorDefinition.USER_REGISTER_FAIL_USERID_ERROR);
        }

        HttpHeaders httpHeaders = new HttpHeaders();

        String ipAddress = "";

        //需要做redis存储注册次数，防止刷请求注册接口
        Integer registerRequestByIp = oAuthService.getRegisterRequestByIp(ipAddress);

        if(registerRequestByIp > FAILED_RETRY_TIMES){
            return createResponseEntity(ErrorDefinition.USER_REGISTER_FAIL_TOO_MANY);
        }

        User user = userService.doRegisterUser(registerInfo);

        if(user == null){
            return createResponseEntity(ErrorDefinition.USER_REGISTER_FAIL);
        }
        //注册成功清除redis中的信息
        oAuthService.clearRegisterRequestByIp(ipAddress);
        return createResponseEntity();

    }

}
