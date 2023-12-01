package com.eastthree.service.serviceImpl;

import com.eastthree.mapper.AttendanceMapper;
import com.eastthree.mapper.CourseMapper;
import com.eastthree.pojo.Attendance;
import com.eastthree.pojo.Course;
import com.eastthree.pojo.resultPojo.ResultCourse;
import com.eastthree.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = {Exception.class})
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private AttendanceMapper attendanceMapper;


    @Override
    public Course getById(Integer courseId) {
        Course course=courseMapper.getById(courseId);
        return course;
    }

    @Override
    public ResultCourse getOneByTimeNow(Integer studentId, LocalDateTime timeNow) {
        ResultCourse resultCourse= courseMapper.getOneByTimeNow(studentId,timeNow);
        //设置学生当前课程状态
        if(resultCourse==null){
            return null;
        }
        Attendance select=new Attendance();
        select.setStudentId(studentId);
        select.setCourseId(resultCourse.getId());
        select=attendanceMapper.selectByDoubleId(select);
//        int comResult=timeNow.compareTo(resultCourse.getBeginTime().plusMinutes(15));
        if(select==null){
            resultCourse.setStatus("未签到");
        }else if(select.getStatus()=="缺勤"){
            resultCourse.setStatus("缺勤");
        }else if(select.getStatus()=="已签到"){
            resultCourse.setStatus("已签到");
        }
//        if(LocalDateTime.timeNow resultCourse.getBeginTime()&&select){
//            resultCourse.setStatus("未签到");
//        }else(timeNow)
        return resultCourse;
    }

    @Override
    public Course getByTime(Course course) {
        Course resultCourse=courseMapper.getByTime(course);
        return resultCourse;
    }

    @Override
    public List<ResultCourse> getByTimeNow(Integer studentId,String week) {
        List<ResultCourse> resultCourse =courseMapper.getByTimeNow(studentId,week);
        for (ResultCourse item : resultCourse) {
            Attendance select=new Attendance();
            select.setStudentId(studentId);
            select.setCourseId(item.getId());
            select=attendanceMapper.selectByDoubleId(select);
            if(select==null)
                item.setStatus("未签到");
            else{
                item.setStatus(select.getStatus());
            }
        }

        return resultCourse;
    }

    @Override
    public Integer getCourseIdByBeginTimeAndTeacherId(String week,String weekday,String section, Integer teacherId) {
        Integer resultcourseId=courseMapper.selectCourseIdByBeginTimeAndTeacherid(week,weekday,section,teacherId);
        return resultcourseId;
    }
}
