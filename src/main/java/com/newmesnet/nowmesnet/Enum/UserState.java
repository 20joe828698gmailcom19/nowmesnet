package com.newmesnet.nowmesnet.Enum;

import lombok.Getter;

/**
 * @author zqh
 * @create 2022-06-24 18:03
 */
@Getter
public enum UserState {
    Busy, // ("忙"),
    Leave, // ("离开"),
    Hidden, // ("隐身"),
    Online, // ("在线"),
    Offline, // ("离线"),
    Logout, // ("退出"),
    Other // ("其它"),
    ;
}
