package com.eastthree.service.serviceImpl;

import com.eastthree.mapper.*;
import com.eastthree.pojo.Counsellor;
import com.eastthree.pojo.Course;
import com.eastthree.pojo.LeaveApplication;
import com.eastthree.pojo.Student;
import com.eastthree.pojo.resultPojo.ResultLeaveDatail;
import com.eastthree.service.LeaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = {Exception.class})
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveMapper leaveMapper;
    @Autowired
    private SupervisionTaskMapper supervisionTaskMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private CounsellorMapper counsellorMapper;
    @Override
    public List<LeaveApplication> selectLeaveById(Integer id) {
        return leaveMapper.getByStudentId(id);
    }

    @Override
    public void addLeave(LeaveApplication leaveApplication) {
        leaveMapper.addLeave(leaveApplication);
    }
    //根据督导id获取督导对应的请假
    @Override
    public List<LeaveApplication> selectLeaveBySupervisionId(Integer SupervisionId) {
        List<LeaveApplication> leaveApplicationList = new ArrayList<>();;
        List<Integer> courseList=supervisionTaskMapper.getCourselistByStudentId(SupervisionId);
        for(int i=0;i<courseList.size();i++){
            leaveApplicationList.addAll(leaveMapper.getByCourseId(courseList.get(i)));
        }

        return leaveApplicationList;
    }

    @Override
    public ResultLeaveDatail getLeaveDetail(Integer leaveId) {
        //获取本张表数据
        LeaveApplication leaveApplication=leaveMapper.getById(leaveId);
        //通过外键获取其他表所有数据
        Student student=studentMapper.getById(leaveApplication.getStudentId());

        Course course=courseMapper.getById(leaveApplication.getCourseId());
        //封装结果
        ResultLeaveDatail resultLeaveDatail=new ResultLeaveDatail();
        if(leaveApplication!=null&&student!=null&&course!=null){

            resultLeaveDatail.setBeginTime(leaveApplication.getAppealBeginTime());
            resultLeaveDatail.setEndTime(leaveApplication.getAppealEndTime());
            resultLeaveDatail.setReason(leaveApplication.getReason());
            resultLeaveDatail.setStudentNo(student.getNo());
            resultLeaveDatail.setStudentName(student.getName());
            resultLeaveDatail.setCourseName(course.getCourseName());
        }


        return resultLeaveDatail;
    }

    @Override
    public void updateLeaveStatus(Integer leaveId,String status) {
        LeaveApplication leaveApplication=leaveMapper.getById(leaveId);
        Integer studentId=leaveApplication.getStudentId();
        Integer courseId=leaveApplication.getCourseId();
        if(status.equals("通过"))
            attendanceMapper.updateStatus(studentId,courseId,"请假");
        leaveMapper.updateLeaveStatus(leaveId,status);
    }

    @Override
    public List<LeaveApplication> selectLeaveByCounsellorId(Integer counsellorId) {
        Counsellor counsellor=counsellorMapper.getById(counsellorId);
        List<Student> studentList=studentMapper.getByCollege(counsellor.getCollege());
        List<LeaveApplication> leaveApplicationList=new ArrayList<>();
        for(int i=0;i<studentList.size();i++){
            List<LeaveApplication> temp=leaveMapper.getByStudentId(studentList.get(i).getId());
            leaveApplicationList.addAll(temp);
        }
        return leaveApplicationList;
    }
}