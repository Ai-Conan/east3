package com.eastthree.service;

import com.eastthree.pojo.Course;
import com.eastthree.pojo.resultPojo.ResultCourse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface CourseService {
    public Course getByTime(Course course);
    public List<ResultCourse> getByTimeNow(Integer studentId, String week);
    ResultCourse getOneByTimeNow(Integer studentId,LocalDateTime timeNow);
    Integer getCourseIdByBeginTimeAndTeacherId(String week,String weekday,String section,Integer teacherId);

    Course getById(Integer courseId);
}
