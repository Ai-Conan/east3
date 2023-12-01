package com.eastthree.Controller;

import com.eastthree.pojo.*;
import com.eastthree.pojo.resultPojo.ResultAttendance;
import com.eastthree.pojo.resultPojo.ResultAttendanceCount;
import com.eastthree.pojo.resultPojo.ResultClassAttendance;
import com.eastthree.service.AttendanceService;
import com.eastthree.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private CourseService courseService;
    @PostMapping("/Attendance")
    public Result updateAttendanceStatus(@RequestBody Attendance attendance) {
        //调用service层的添加功能
        attendanceService.updateAttendanceStatus(attendance);
        return Result.success();
    }
    @GetMapping("/whoNoCheck/{courseId}")
    public Result getWhoNoCheck(@PathVariable Integer courseId) {
        //调用service层的添加功能
        Student student=attendanceService.getWhoNoCheck(courseId);
        return Result.success(student);
    }
    //获取这个课程的学生名单及其这节课的考勤情况
    @GetMapping("/courseAttendance/{courseId}")
    public Result getCourseAttendance(@PathVariable Integer courseId) {
        //调用service层的添加功能
        List<ResultAttendance> resultAttendanceList= attendanceService.getCourseAttendance(courseId);
        return Result.success(resultAttendanceList);
    }
    //统计一个课程的总的考勤情况
    @GetMapping("/Attendance/{courseId}")
    public Result getAttendanceCount(@PathVariable Integer courseId){
        //调用service层的添加功能
        List<ResultAttendance> resultAttendanceList= attendanceService.getCourseAttendance(courseId);
        Integer attendanceCount=0;
        Integer absentCount=0;
        Integer leaveApplicationCount=0;
        Integer noCheck=0;
        for(int i=0;i<resultAttendanceList.size();i++){
            if(resultAttendanceList.get(i).getStatus().equals("已签到")){
                attendanceCount++;
            }else if(resultAttendanceList.get(i).getStatus().equals("未签到")){
                noCheck++;
            }else if(resultAttendanceList.get(i).getStatus().equals("请假")){
                absentCount++;
            }else if(resultAttendanceList.get(i).getStatus().equals("缺勤")){
                leaveApplicationCount++;
            }
        }
        ResultAttendanceCount resultAttendanceCount=new ResultAttendanceCount();
        resultAttendanceCount.setAttendanceCount(attendanceCount);
        resultAttendanceCount.setAbsentCount(absentCount);
        resultAttendanceCount.setLeaveApplicationCount(leaveApplicationCount);
        resultAttendanceCount.setNoCheck(noCheck);
        return Result.success(resultAttendanceCount);
    }
    //获取一个班的考勤数据，包括学生学号，姓名，以及签到，未签到以及请假次数的统计
    @GetMapping("/Attendance/{grade}/{major}/{Class}")
    public Result getClassAttendance(@PathVariable String grade,@PathVariable String major,@PathVariable String Class){
        //调用service层的添加功能
        if(grade.equals("undefined")){
            grade=null;
        }
        if(major.equals("undefined")){
            major=null;
        }
        if(Class.equals("undefined")){
            Class=null;
        }
        List<ResultClassAttendance> resultClassAttendanceList=attendanceService.getClassAttendance(grade,major,Class);

        return Result.success(resultClassAttendanceList);
    }

}