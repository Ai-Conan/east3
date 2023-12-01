package com.eastthree.mapper;

import com.eastthree.pojo.Attendance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttendanceMapper {

    void addAttendance(Attendance attendance);
    Attendance selectByDoubleId(Attendance attendance);
    void modifyAttendance(Attendance attendance);

    List<Integer> getWhoNoCheck(Integer courseId);
    void updateStatus(Integer studentId, Integer courseId, String status);
    List<Attendance> selectByStatusAndCourseId(Integer courseId,String status);
    Integer countStudentAttendance(Integer studentId,String status);
    Attendance getByStudentIdAndBeginTimeAndEndTime(Attendance attendance);
}
