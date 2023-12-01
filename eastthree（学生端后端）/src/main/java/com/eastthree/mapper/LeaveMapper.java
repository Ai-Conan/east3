package com.eastthree.mapper;

import com.eastthree.pojo.LeaveApplication;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LeaveMapper {
    void addLeave(LeaveApplication leaveApplication);
    List<LeaveApplication> getByStudentId(Integer studentId);
    List<LeaveApplication> getByCourseId(Integer courseId);
    LeaveApplication getById(Integer leaveId);

    void updateLeaveStatus(Integer leaveId,String status);
}
