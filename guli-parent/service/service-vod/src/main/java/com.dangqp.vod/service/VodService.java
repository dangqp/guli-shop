package com.dangqp.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Title:com.dangqp.vod.service.VodService
 * Description: 描述【VodService】
 * Copyright: Copyright (c) 2018
 * Company: 
 * @author dangqp
 * @version 1.0
 * @created  2021/1/11 18:45
 */
public interface VodService {
    //上传视频到阿里云
    String uploadVideoAly(MultipartFile file);

    //删除多个阿里云视频的方法
    void removeMoreAlyVideo(List videoIdList);
}
