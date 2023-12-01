package com.eastthree.service.serviceImpl;

import com.eastthree.mapper.Student_TeacherMapper;
import com.eastthree.service.Student_TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(rollbackFor = {Exception.class})
public class Student_TeacherServiceImpl implements Student_TeacherService {
    @Autowired
    private Student_TeacherMapper student_teacherMapper;

    @Override
    public Integer selectTeacherByStudent(Integer studentId) {
        return student_teacherMapper.selectTeacherByStudent(studentId);
    }
}
