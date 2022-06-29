package com.newmesnet.nowmesnet.utils;

import com.sun.deploy.net.HttpUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 * @author zqh
 * @create 2022-06-29 17:01
 */
public class HttpUtil {

    /**
     * 向第三方发起一个http请求
     */
    public Object connHttpForOther(){
        RestTemplate longWaitRestTemplate = new RestTemplate();
        String url = "";
        String accessToken = "";
        HttpEntity<Object> entity = null;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        entity = new HttpEntity<>(headers);
        longWaitRestTemplate.exchange(url, HttpMethod.GET, entity, String.class);



        return null;
    }

    class LineVisitorData{

        private String displayName;
        private String userId;
        private String language;
        private String pictureUrl;
        private String statusMessage;
        private String url;

    }
}
