package com.eastthree.mapper;

import com.eastthree.pojo.Course;
import com.eastthree.pojo.resultPojo.ResultCourse;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CourseMapper {
    Course getByTime(Course course);
    List<ResultCourse> getByTimeNow(Integer studentId, String week);
    ResultCourse getOneByTimeNow(Integer studentId,LocalDateTime timeNow);
    Course getById(Integer courseId);
    Integer selectCourseIdByBeginTimeAndTeacherid(String week,String weekday,String section,Integer teacherId);
}
