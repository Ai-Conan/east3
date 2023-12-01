package com.eastthree.service;

import com.eastthree.pojo.LeaveApplication;
import com.eastthree.pojo.resultPojo.ResultLeaveDatail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LeaveService {
    public void addLeave(LeaveApplication leaveApplication);
    public List<LeaveApplication> selectLeaveById(Integer id);
    public List<LeaveApplication> selectLeaveBySupervisionId(Integer SupervisionId);

    public ResultLeaveDatail getLeaveDetail(Integer leaveId);


    public void updateLeaveStatus(Integer leaveId,String status);

    List<LeaveApplication> selectLeaveByCounsellorId(Integer counsellorId);
}
