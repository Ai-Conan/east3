package com.eastthree.Controller;

import com.eastthree.pojo.*;
import com.eastthree.pojo.resultPojo.ResultLeaveDatail;
import com.eastthree.service.AttendanceService;
import com.eastthree.service.CourseService;
import com.eastthree.service.LeaveService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class LeaveController {

    @Autowired
    private LeaveService leaveService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private AttendanceService attendanceService;
    @PostMapping("/addLeave")
    public Result AddLeave(@RequestBody LeaveApplication leaveApplication, @RequestHeader String token) {
        //设置学生id
        Claims claims=JwtUtils.parseJwt(token);
        String id=claims.get("id").toString();
        Integer StudentId=Integer.parseInt(id);
        leaveApplication.setStudentId(StudentId);
        //设置课程id
        Attendance target=new Attendance();
        System.out.println(leaveApplication.getReason());
        target.setBeginTime(leaveApplication.getAppealBeginTime());
        target.setEndTime(leaveApplication.getAppealEndTime());
        target.setStudentId(StudentId);
        System.out.println(StudentId);
        target=attendanceService.getByStudentIdAndBeginTimeAndEndTime(target);
        Course course=courseService.getById(target.getCourseId());
        leaveApplication.setLeaveCourseName(course.getCourseName());
        leaveApplication.setCourseId(target.getCourseId());
        leaveApplication.setStatus("未通过");
        //封装完毕后调用service层的add方法
        leaveService.addLeave(leaveApplication);
        return Result.success();
    }
    @GetMapping("/selectLeave")
    public Result selectLeave(@RequestHeader String token) {
        //设置学生id
        Claims claims=JwtUtils.parseJwt(token);
        String id=claims.get("id").toString();
        Integer StudentId=Integer.parseInt(id);
        List<LeaveApplication> result=leaveService.selectLeaveById(StudentId);
        return Result.success(result);
    }
    @GetMapping("/supervisionLeave")
    public Result supervisionLeave(@RequestHeader String token){
        //设置督导id
        Claims claims=JwtUtils.parseJwt(token);
        String id=claims.get("id").toString();
        Integer supervisionId=Integer.parseInt(id);
        List<LeaveApplication> leaveApplicationList=leaveService.selectLeaveBySupervisionId(supervisionId);
        return Result.success(leaveApplicationList);
    }
    @GetMapping("/leaveDetail/{leaveId}")
    public Result LeaveDetail(@RequestHeader String token, @PathVariable Integer leaveId){
        ResultLeaveDatail resultLeaveDatail=leaveService.getLeaveDetail(leaveId);
        return Result.success(resultLeaveDatail);
    }
    @PutMapping("/judgeLeave/{leaveId}/{status}")
    public Result JudgeLeave(@PathVariable Integer leaveId, @PathVariable String status){

        leaveService.updateLeaveStatus(leaveId,status);
        return Result.success();
    }
    @GetMapping("/counsellorLeave")
    public Result counsellorLeave(@RequestHeader String token){
        //设置督导id
        Claims claims=JwtUtils.parseJwt(token);
        String id=claims.get("id").toString();
        Integer counsellorId=Integer.parseInt(id);
        List<LeaveApplication> leaveApplicationList=leaveService.selectLeaveByCounsellorId(counsellorId);
        return Result.success(leaveApplicationList);
    }

}
