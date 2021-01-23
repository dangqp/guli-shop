package com.dangqp.eduservice.controller;


import com.dangqp.eduservice.client.VodClient;
import com.dangqp.eduservice.entity.EduVideo;
import com.dangqp.eduservice.service.EduVideoService;
import com.dangqp.servicebase.exceptionhandler.GuliException;
import commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author dangqp
 * @since 2020-09-30
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    //注入vodClient
    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return Result.ok();
    }

    //删除小节，删除对应阿里云视频
    @DeleteMapping("{id}")
    public Result deleteVideo(@PathVariable String id) {
        //根据小节id获取视频id，调用方法实现视频删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断小节里面是否有视频id
        if(!StringUtils.isEmpty(videoSourceId)) {
            //根据视频id，远程调用实现视频删除
            Result result = vodClient.removeAlyVideo(videoSourceId);
            if(result.getCode() == 20001) {
                throw new GuliException(20001,"删除视频失败，熔断器...");
            }
        }
        //删除小节
        videoService.removeById(id);
        return Result.ok();
    }

    //获取小节信息
    @GetMapping("{id}")
    public Result getVideoInfo(@PathVariable String id) {
        //根据小节id获取视频id，调用方法实现视频删除
        Optional<EduVideo> eduVideo = Optional.ofNullable(videoService.getById(id));
        if (eduVideo.isPresent())
            return Result.ok().data( "video",eduVideo.get() );
        return Result.error().message("信息不存在");
    }

    //修改小节信息
    @PostMapping("updVideo")
    public Result updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return Result.ok();
    }
}

