package com.newmesnet.nowmesnet.persistence.jpa;

import com.newmesnet.nowmesnet.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zqh
 * @create 2022-06-29 10:20
 */
@Repository
public interface LoginLogRepository extends JpaRepository<LoginLog, String> {
    LoginLog findByUserId(String userId);
}
