package com.newmesnet.nowmesnet.persistence.jpa;

import com.newmesnet.nowmesnet.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zqh
 * @create 2022-06-28 15:49
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    UserInfo findByUserId(String userId);

}
