package com.newmesnet.nowmesnet.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户信息表
 * @author zqh
 * @create 2022-06-24 17:58
 */
@Entity
@Data
@Table(name = "user_info")
public class UserInfo {
    @Id
    private String userId;
    private String userName;
    private String userCity;
    private String userRealCity;
    private String userDesc;
    private String userAvatar;
    private String userSex;
    private String userRole;
    private String userBirthday;
    private Integer userLevel = 0;
    private String userWechat;
    private String userQQ;
    private String userGithub;
    private String userWeibo;
    private Date userUpdateTime;

}
