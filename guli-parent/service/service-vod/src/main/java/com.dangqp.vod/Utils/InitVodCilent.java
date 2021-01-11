package com.dangqp.vod.Utils;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

/**
 * Title:com.dangqp.vod.Utils.InitVodCilent
 * Description: 描述【InitVodCilent】
 * Copyright: Copyright (c) 2018
 * Company: 
 * @author dangqp
 * @version 1.0
 * @created  2021/1/11 18:42
 */
public class InitVodCilent {

    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
