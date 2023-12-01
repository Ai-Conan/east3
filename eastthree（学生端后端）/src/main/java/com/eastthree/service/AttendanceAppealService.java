package com.eastthree.service;

import com.eastthree.pojo.AttendanceAppeal;
import com.eastthree.pojo.resultPojo.ResultAttendanceAppealDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttendanceAppealService {
    public void addAttendanceAppeal(AttendanceAppeal attendanceAppeal);
    public List<AttendanceAppeal> selectAttendanceAppealById(Integer id);

    List<AttendanceAppeal> selectLeaveBySupervisionId(Integer SupervisionId);

    ResultAttendanceAppealDetail getAttendanceAppealDetail(Integer attendanceAppealId);


    void updateAttendanceAppealStatus(Integer attendanceAppealId, String status);

    List<AttendanceAppeal> selectAttendanceAppealByCounsellorId(Integer counsellorId);
}
