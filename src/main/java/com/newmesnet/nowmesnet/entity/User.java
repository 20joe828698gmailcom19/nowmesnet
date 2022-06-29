package com.newmesnet.nowmesnet.entity;

import com.newmesnet.nowmesnet.Enum.UserState;
import com.newmesnet.nowmesnet.Enum.UserStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户表
 * @create 2022-06-23 11:48
 */
@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    private String userId;
    private String userPassword;
    private String userEmail;
    private String userPhone;
    private UserState userState;
    private UserStatus userStatus;
    private String userIp;
    private String userDevice;
    private Date userLastLoginDateTime;
    private Date userUpdateDateTime;
    private Date userCreateDateTime;
}
