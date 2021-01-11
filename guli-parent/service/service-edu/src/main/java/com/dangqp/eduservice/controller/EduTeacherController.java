package com.dangqp.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dangqp.eduservice.entity.EduTeacher;
import com.dangqp.eduservice.entity.vo.TeacherQuery;
import com.dangqp.eduservice.service.impl.EduTeacherServiceImpl;
import commonutils.Result;
import com.dangqp.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author dangqp
 * @since 2020-12-11
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherServiceImpl teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public Result findAllTeacjer() {

        return Result.ok().data( "items", teacherService.list( null ) );
    }

    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public Result removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                                @PathVariable String id) {
        boolean flag = teacherService.removeById( id );
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    //3 分页查询讲师的方法
    //current 当前页
    //limit 每页记录数
    @GetMapping("pageTeacher/{current}/{limit}")
    public Result pageListTeacher(@PathVariable long current,
                                  @PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>( current, limit );

        try {
            // int i = 10/0;
        } catch (Exception e) {
            //执行自定义异常
            throw new GuliException( 20001, "执行了自定义异常处理...." );
        }


        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        teacherService.page( pageTeacher, null );

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合

//        Map map = new HashMap();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);

        return Result.ok().data( "total", total ).data( "rows", records );
    }

    //4 条件查询带分页的方法
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public Result pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                       @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>( current, limit );

        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty( name )) {
            //构建条件
            wrapper.like( "name", name );
        }
        if (!StringUtils.isEmpty( level )) {
            wrapper.eq( "level", level );
        }
        if (!StringUtils.isEmpty( begin )) {
            wrapper.ge( "gmt_create", begin );
        }
        if (!StringUtils.isEmpty( end )) {
            wrapper.le( "gmt_create", end );
        }

        //排序
        wrapper.orderByDesc( "gmt_create" );

        //调用方法实现条件查询分页
        teacherService.page( pageTeacher, wrapper );

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合
        return Result.ok().data( "total", total ).data( "rows", records );
    }

    //添加讲师接口的方法
    @PostMapping("addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save( eduTeacher );
        if (save) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    //根据讲师id进行查询
    @GetMapping("getTeacher/{id}")
    public Result getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById( id );
        return Result.ok().data( "teacher", eduTeacher );
    }

    //讲师修改功能
    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById( eduTeacher );
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}

