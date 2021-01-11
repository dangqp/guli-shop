package com.dangqp.eduservice.client;

import commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
   //出错之后会执行
    @Override
    public Result removeAlyVideo(String id) {
        return Result.error().message("删除视频出错了");
    }

    @Override
    public Result deleteBatch(List<String> videoIdList) {
        return Result.error().message("删除多个视频出错了");
    }
}
