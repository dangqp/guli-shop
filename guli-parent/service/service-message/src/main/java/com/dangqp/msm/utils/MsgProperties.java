package com.dangqp.msm.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Title:com.dangqp.msm.utils
 * Description:
 * Copyright: Copyright (c) 2021
 *
 * @author dangqp
 * @version 1.0
 * @created 2021/01/16  19:16
 */
@Component
public class MsgProperties implements InitializingBean {

    @Value("${aliyun.msg.file.keyid}")
    private String keyId;

    @Value("${aliyun.msg.file.keysecret}")
    private String keySecret;


    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = this.keyId;
        ACCESS_KEY_SECRET = this.keySecret;
    }
}
