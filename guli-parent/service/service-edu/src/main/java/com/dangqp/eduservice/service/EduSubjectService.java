package com.dangqp.eduservice.service;

import com.dangqp.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dangqp.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author dangqp
 * @since 2021-01-10
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
