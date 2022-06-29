package com.newmesnet.nowmesnet.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 登录日志表
 * @author zqh
 * @create 2022-06-29 10:11
 */
@Entity
@Data
@Table(name = "login_log")
public class LoginLog {
    @Id
    private int id;
    private String userId;
    private Date loginTime;
    private String loginIp;
    private String loginDevice;
    private String loginCity;
    private String loginFlag;
    private String loginType;
}
