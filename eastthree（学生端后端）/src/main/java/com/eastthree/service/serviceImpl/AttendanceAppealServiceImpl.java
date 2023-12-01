package com.eastthree.service.serviceImpl;

import com.eastthree.mapper.*;
import com.eastthree.pojo.*;
import com.eastthree.pojo.resultPojo.ResultAttendanceAppealDetail;
import com.eastthree.service.AttendanceAppealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class})
public class AttendanceAppealServiceImpl implements AttendanceAppealService {
    @Autowired
    private AttendanceAppealMapper attendanceAppealMapper;
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
    public void addAttendanceAppeal(AttendanceAppeal attendanceAppeal) {
        attendanceAppealMapper.addAttendanceAppeal(attendanceAppeal);
    }

    @Override
    public List<AttendanceAppeal> selectAttendanceAppealById(Integer id) {
        return attendanceAppealMapper.getByStudentId(id);
    }
    @Override
    public List<AttendanceAppeal> selectLeaveBySupervisionId(Integer SupervisionId) {
        List<AttendanceAppeal> attendanceAppealList = new ArrayList<>();;
        List<Integer> courseList=supervisionTaskMapper.getCourselistByStudentId(SupervisionId);
        for(int i=0;i<courseList.size();i++){
            attendanceAppealList.addAll(attendanceAppealMapper.getByCourseId(courseList.get(i)));
        }

        return attendanceAppealList;
    }
    //查询申诉的详情
    @Override
    public ResultAttendanceAppealDetail getAttendanceAppealDetail(Integer attendanceAppealId) {
        //获取本张表数据
        AttendanceAppeal attendanceAppeal=attendanceAppealMapper.getById(attendanceAppealId);
        //通过外键获取其他表所有数据
        Student student=studentMapper.getById(attendanceAppeal.getStudentId());

        Course course=courseMapper.getById(attendanceAppeal.getCourseId());
        //封装结果
        ResultAttendanceAppealDetail resultAttendanceAppealDetail=new ResultAttendanceAppealDetail();
        if(attendanceAppeal!=null&&student!=null&&course!=null){
            resultAttendanceAppealDetail.setBeginTime(attendanceAppeal.getAppealBeginTime());
            resultAttendanceAppealDetail.setEndTime(attendanceAppeal.getAppealEndTime());
            resultAttendanceAppealDetail.setReason(attendanceAppeal.getReason());
            resultAttendanceAppealDetail.setStudentNo(student.getNo());
            resultAttendanceAppealDetail.setStudentName(student.getName());
            resultAttendanceAppealDetail.setCourseName(course.getCourseName());
        }
        return resultAttendanceAppealDetail;
    }
    //更新申诉状态
    @Override
    public void updateAttendanceAppealStatus(Integer attendanceAppealId, String status) {
        AttendanceAppeal attendanceAppeal=attendanceAppealMapper.getById(attendanceAppealId);
        Integer studentId=attendanceAppeal.getStudentId();
        Integer courseId=attendanceAppeal.getCourseId();
        if(status.equals("通过"))
            attendanceMapper.updateStatus(studentId,courseId,"已签到");
        attendanceAppealMapper.updateAttendanceAppealStatus(attendanceAppealId,status);
    }

    @Override
    public List<AttendanceAppeal> selectAttendanceAppealByCounsellorId(Integer counsellorId) {
        Counsellor counsellor=counsellorMapper.getById(counsellorId);
        List<Student> studentList=studentMapper.getByCollege(counsellor.getCollege());
        List<AttendanceAppeal> attendanceAppealList=new ArrayList<>();
        for(int i=0;i<studentList.size();i++){
            List<AttendanceAppeal> temp=attendanceAppealMapper.getByStudentId(studentList.get(i).getId());
            attendanceAppealList.addAll(temp);
        }
        return attendanceAppealList;
    }
}
