package com.newmesnet.nowmesnet.base;

import com.newmesnet.nowmesnet.service.IOAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zqh
 * @create 2022-06-23 15:13
 */
@Slf4j
public abstract class AbstractController extends BaseAbstractController {

    @Autowired
    private IOAuthService oAuthService;

    @Autowired
    private HttpServletRequest request;

    //需要写验证登录的方法，达到代码共用的目的
    public void checkLogined(String userId, HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            throw  new Exception(String.valueOf(HttpStatus.UNAUTHORIZED));
        }
        HttpHeaders httpHeaders = new HttpHeaders();


    }

}
