package com.newmesnet.nowmesnet;

import com.newmesnet.nowmesnet.utils.BCrypt;
import com.newmesnet.nowmesnet.utils.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@Slf4j
@SpringBootTest
class NowmesnetApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testEncryptUtil(){
        EncryptUtil encryptUtil = new EncryptUtil();

        String text = "这是需要加密的明文";
        String salt = UUID.randomUUID().toString();
        String saltTmp = RandomStringUtils.randomAlphanumeric(10);

        String ans = encryptUtil.md5Encrypt(text, salt);

        log.info("这是加密结果：{}", ans);

    }

    @Test
    void testBCrypt(){

        String gensalt = BCrypt.gensalt(12);//这个是盐  29个字符，随机生成，其中有前4位是固定的，前7位是存在规律的。
        System.out.println(gensalt);
        String password = BCrypt.hashpw("123456asd_", gensalt);  //根据盐对密码进行加密
        System.out.println(password);//加密后的字符串前29位就是盐

        //校验密码
        boolean abc = BCrypt.checkpw("123456asd_", password);//如果不修改密码那么这个密文一直都一样的，密文在修改密码和创建账号的时候生成
        System.out.println(abc);

    }

}
