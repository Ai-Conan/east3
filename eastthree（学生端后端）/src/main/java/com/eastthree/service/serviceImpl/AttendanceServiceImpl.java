package com.eastthree.service.serviceImpl;

import com.eastthree.mapper.AttendanceMapper;
import com.eastthree.mapper.CourseMapper;
import com.eastthree.mapper.StudentMapper;
import com.eastthree.pojo.Attendance;
import com.eastthree.pojo.Student;
import com.eastthree.pojo.resultPojo.ResultAttendance;
import com.eastthree.pojo.resultPojo.ResultClassAttendance;
import com.eastthree.service.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class})
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public void updateAttendanceStatus(Attendance attendance){
        attendance.setTime(LocalDateTime.now());
        Attendance select=attendanceMapper.selectByDoubleId(attendance);
        if(select==null)
            attendanceMapper.addAttendance(attendance);
        else
            attendanceMapper.modifyAttendance(attendance);
    };

    @Override
    public Student getWhoNoCheck(Integer courseId) {
        List<Integer> studentId=attendanceMapper.getWhoNoCheck(courseId);
        Student result=studentMapper.getById(studentId.get(0));
        return result;
    }

    @Override
    public List<ResultAttendance> getCourseAttendance(Integer courseId) {
        List<Attendance> attendanceListAbsence=attendanceMapper.selectByStatusAndCourseId(courseId,"缺勤");
        List<Attendance> attendanceListSigned=attendanceMapper.selectByStatusAndCourseId(courseId,"已签到");
        List<Attendance> attendanceListUnSigned=attendanceMapper.selectByStatusAndCourseId(courseId,"未签到");
        List<Attendance> attendanceListLeave=attendanceMapper.selectByStatusAndCourseId(courseId,"请假");
        List<Attendance> result=attendanceListAbsence;
        result.addAll(attendanceListLeave);
        result.addAll(attendanceListUnSigned);
        result.addAll(attendanceListSigned);
        List<ResultAttendance> realResult=new ArrayList<>();
        for(int i=0;i<result.size();i++){
            ResultAttendance temp=new ResultAttendance();
            temp.setId(result.get(i).getId());
            temp.setStudentId(result.get(i).getStudentId());
            temp.setCourseId(result.get(i).getCourseId());
            temp.setStatus(result.get(i).getStatus());
            Student student=studentMapper.getById(result.get(i).getStudentId());
            temp.setStudentName(student.getName());
            temp.setStudentNo(student.getNo());
            realResult.add(temp);
        }
        return realResult;
    }
    //获取这个班级的考勤情况

    @Override
    public List<ResultClassAttendance> getClassAttendance(String grade, String major, String Class) {
        List<Student> studentList=studentMapper.getByGradeAndMajorAndClass(grade,major,Class);
        List<ResultClassAttendance> resultClassAttendanceList=new ArrayList<>();
        for(int i=0;i<studentList.size();i++){
            Integer signedCount=attendanceMapper.countStudentAttendance(studentList.get(i).getId(),"已签到");
            Integer unsignedCount=attendanceMapper.countStudentAttendance(studentList.get(i).getId(),"未签到");
            Integer leaveCount=attendanceMapper.countStudentAttendance(studentList.get(i).getId(),"请假");
            ResultClassAttendance temp=new ResultClassAttendance(studentList.get(i).getNo(),studentList.get(i).getName(),signedCount,unsignedCount,leaveCount);
            resultClassAttendanceList.add(temp);
        }
        return resultClassAttendanceList;
    }

    @Override
    public Attendance getByStudentIdAndBeginTimeAndEndTime(Attendance target) {
        target=attendanceMapper.getByStudentIdAndBeginTimeAndEndTime(target);
        return target;
    }
}
