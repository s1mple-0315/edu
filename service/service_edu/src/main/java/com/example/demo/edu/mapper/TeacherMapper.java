package com.example.demo.edu.mapper;

import com.example.demo.edu.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-03-24
 */

@RestController
@RequestMapping("/eduservice/teacher")
public interface TeacherMapper extends BaseMapper<Teacher> {

}
