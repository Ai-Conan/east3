package com.eastthree.Controller;

import com.eastthree.pojo.*;
import com.eastthree.pojo.resultPojo.ResultAttendanceAppealDetail;
import com.eastthree.service.AttendanceAppealService;
import com.eastthree.service.AttendanceService;
import com.eastthree.service.CourseService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AttendanceAppealController {
    @Autowired
    CourseService courseService;
    @Autowired
    AttendanceAppealService attendanceAppealService;
    @Autowired
    AttendanceService attendanceService;
//    @RequestMapping("/hello")
//    public String hello(){
//        return "hello";
//    }
    @PostMapping("/addAttendanceAppeal")
    public Result AddAttendanceAppeal(@RequestBody AttendanceAppeal attendanceAppeal, @RequestHeader String token) {
        //设置学生id
        Claims claims=JwtUtils.parseJwt(token);
        String id=claims.get("id").toString();
        Integer StudentId=Integer.parseInt(id);
        attendanceAppeal.setStudentId(StudentId);
        //设置课程id
        Attendance target=new Attendance();
        System.out.println(attendanceAppeal.getReason());
        target.setBeginTime(attendanceAppeal.getAppealBeginTime());
        target.setEndTime(attendanceAppeal.getAppealEndTime());
        target.setStudentId(StudentId);
        System.out.println(StudentId);
        target=attendanceService.getByStudentIdAndBeginTimeAndEndTime(target);
        Course course=courseService.getById(target.getCourseId());
        attendanceAppeal.setLeaveCourseName(course.getCourseName());
        attendanceAppeal.setCourseId(target.getCourseId());
        attendanceAppeal.setStatus("未通过");
        //封装完毕后调用service层的add方法
        attendanceAppealService.addAttendanceAppeal(attendanceAppeal);
        return Result.success();
    }
    @GetMapping("/selectAttendanceAppeal")
    public Result selectLeave(@RequestHeader String token) {
        //设置学生id
        Claims claims=JwtUtils.parseJwt(token);
        String id=claims.get("id").toString();
        Integer StudentId=Integer.parseInt(id);
        List<AttendanceAppeal> result=attendanceAppealService.selectAttendanceAppealById(StudentId);
        return Result.success(result);
    }
    @GetMapping("/supervisionAttendanceAppeal")
    public Result supervisionLeave(@RequestHeader String token){
        //设置督导id
        Claims claims=JwtUtils.parseJwt(token);
        String id=claims.get("id").toString();
        Integer supervisionId=Integer.parseInt(id);
        List<AttendanceAppeal> attendanceAppealList=attendanceAppealService.selectLeaveBySupervisionId(supervisionId);
        return Result.success(attendanceAppealList);
    }
    //查询对应申诉的详细信息
    @GetMapping("/attendanceAppealDetail/{attendanceAppealId}")
    public Result LeaveDetail(@RequestHeader String token, @PathVariable Integer attendanceAppealId){
        ResultAttendanceAppealDetail resultAttendanceAppealDetail=attendanceAppealService.getAttendanceAppealDetail(attendanceAppealId);
        return Result.success(resultAttendanceAppealDetail);
    }
    @PutMapping("/judgeAttendanceAppeal/{attendanceAppealId}/{status}")
    public Result JudgeLeave(@RequestHeader String token,@PathVariable Integer attendanceAppealId, @PathVariable String status){
        attendanceAppealService.updateAttendanceAppealStatus(attendanceAppealId,status);
        return Result.success();
    }
    @GetMapping("/counsellorAttendanceAppeal")
    public Result counsellorAttendanceAppeal(@RequestHeader String token){
        //设置督导id
        Claims claims=JwtUtils.parseJwt(token);
        String id=claims.get("id").toString();
        Integer counsellorId=Integer.parseInt(id);
        List<AttendanceAppeal> attendanceAppealList=attendanceAppealService.selectAttendanceAppealByCounsellorId(counsellorId);
        return Result.success(attendanceAppealList);
    }

}
