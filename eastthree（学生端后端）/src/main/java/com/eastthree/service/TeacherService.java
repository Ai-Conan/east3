package com.eastthree.service;

import com.eastthree.pojo.Teacher;
import org.springframework.stereotype.Service;


@Service
public interface TeacherService {
    public Teacher login(Teacher teacher);

    Integer getIdByTeacherName(String teacherName);
}