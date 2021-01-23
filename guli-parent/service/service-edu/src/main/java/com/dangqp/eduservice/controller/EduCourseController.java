package com.dangqp.eduservice.controller;


import com.dangqp.eduservice.entity.EduCourse;
import com.dangqp.eduservice.entity.vo.CourseInfoVo;
import com.dangqp.eduservice.entity.vo.CoursePublishVo;
import com.dangqp.eduservice.service.EduCourseService;
import commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author dangqp
 * @since 2020-09-30
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/course")
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    //课程列表 基本实现
    @GetMapping
    public Result getCourseList() {
        List<EduCourse> list = courseService.list(null);
        return Result.ok().data("list",list);
    }

    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //返回添加之后课程id，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return Result.ok().data("courseId",id);
    }

    //根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return Result.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public Result getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return Result.ok().data("publishCourse",coursePublishVo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public Result publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程发布状态
        courseService.updateById(eduCourse);
        return Result.ok();
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public Result deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return Result.ok();
    }

}

