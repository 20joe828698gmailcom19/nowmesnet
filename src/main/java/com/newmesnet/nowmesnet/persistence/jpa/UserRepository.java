package com.newmesnet.nowmesnet.persistence.jpa;

import com.newmesnet.nowmesnet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zqh
 * @create 2022-06-23 14:23
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUserId(String userId);

    User findByUserEmail(String userEmail);

    User findByUserPhone(String userPhone);

}
