package com.dangqp.eduservice.mapper;

import com.dangqp.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dangqp.eduservice.entity.frontvo.CourseWebVo;
import com.dangqp.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author dangqp
 * @since 2020-09-30
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CourseWebVo getBaseCourseInfo(String courseId);

    CoursePublishVo getPublishCourseInfo(String id);
}
