package com.eastthree.service.serviceImpl;

import com.eastthree.mapper.TeacherMapper;
import com.eastthree.pojo.Teacher;
import com.eastthree.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class})
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher login(Teacher teacher) {
        //调用dao层功能：登录
        Teacher loginTeacher=teacherMapper.getByUsernameAndPassword(teacher);
        //返回查询结果Controller
        return loginTeacher;
    }

    @Override
    public Integer getIdByTeacherName(String teacherName) {
        Integer teacherId=teacherMapper.getIdByName(teacherName);
        return teacherId;
    }
}