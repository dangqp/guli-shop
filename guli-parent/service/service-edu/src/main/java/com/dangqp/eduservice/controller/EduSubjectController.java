package com.dangqp.eduservice.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.dangqp.eduservice.entity.subject.OneSubject;
import com.dangqp.eduservice.service.EduSubjectService;
import commonutils.Result;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author dangqp
 * @since 2021-01-10
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //add subject
    @PostMapping("addSubject")
    public Result addSubject(MultipartFile file){

        //上传file文件
        eduSubjectService.saveSubject(file,eduSubjectService);

        return Result.ok();
    }

    //课程分类列表（树形）
    @GetMapping("getAllSubject")
    public Result getAllSubject() {
        //list集合泛型是一级分类
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return Result.ok().data("list",list);
    }
}

