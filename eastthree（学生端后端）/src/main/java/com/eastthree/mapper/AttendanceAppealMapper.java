package com.eastthree.mapper;

import com.eastthree.pojo.AttendanceAppeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttendanceAppealMapper {
   void addAttendanceAppeal(AttendanceAppeal attendanceAppeal);
   List<AttendanceAppeal> getByStudentId(Integer studentId);
    //根据课程id进行获取
    List<AttendanceAppeal> getByCourseId(Integer courseId);

    AttendanceAppeal getById(Integer attendanceAppealId);

    void updateAttendanceAppealStatus(Integer attendanceAppealId, String status);
}
