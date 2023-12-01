package com.eastthree.service;

import com.eastthree.pojo.Attendance;
import com.eastthree.pojo.Student;
import com.eastthree.pojo.resultPojo.ResultAttendance;
import com.eastthree.pojo.resultPojo.ResultClassAttendance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttendanceService {
    public void updateAttendanceStatus(Attendance attendance);

    public Student getWhoNoCheck(Integer courseId);

    List<ResultAttendance> getCourseAttendance(Integer courseId);
    //获取某个班级的考勤情况
    List<ResultClassAttendance> getClassAttendance(String grade,String major,String Class);

    Attendance getByStudentIdAndBeginTimeAndEndTime(Attendance target);
}
