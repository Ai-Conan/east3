package com.eastthree.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Student_TeacherMapper {
    Integer selectTeacherByStudent(Integer studentId);
}
