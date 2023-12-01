package com.eastthree.Controller;

import com.eastthree.pojo.*;
import com.eastthree.pojo.resultPojo.ResultCourse;
import com.eastthree.service.CourseService;
import com.eastthree.service.TeacherService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    TeacherService teacherService;
    @GetMapping("/courses")
    public Result getCourseWeek(@RequestParam("week") String week,@RequestHeader String token){
        Course select=new Course();
        //设置学生id
        Claims claims=JwtUtils.parseJwt(token);
        String id=claims.get("id").toString();
        Integer StudentId=Integer.parseInt(id);

        // 获取当前时间
//        LocalDateTime today = LocalDateTime.now();
        // 计算本周的开始时间
//        LocalDateTime startOfWeek = today.with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
        // 计算本周的结束时间
//        LocalDateTime endOfWeek = today.with(DayOfWeek.SUNDAY).plusDays(1).toLocalDate().atStartOfDay().minusSeconds(1);
        List<ResultCourse> resultCourse =courseService.getByTimeNow(StudentId,week);
        return Result.success(resultCourse);
    }
    @GetMapping("/course")
    public Result getOneCourse(@RequestHeader String token){
        ResultCourse resultCourse=new ResultCourse();
        //设置学生id
        Claims claims=JwtUtils.parseJwt(token);
        String id=claims.get("id").toString();
        Integer StudentId=Integer.parseInt(id);
        //设置时间
        // 获取当前时间
        LocalDateTime today = LocalDateTime.now();
        resultCourse=courseService.getOneByTimeNow(StudentId,today);
        return Result.success(resultCourse);
    }

    @GetMapping("teacherGetCourse/{week}/{weekday}/{section}")
    public Result teacherGetCourse(@RequestHeader String token,@PathVariable String week,@PathVariable String  weekday,@PathVariable String section){
        Claims claims=JwtUtils.parseJwt(token);
        String id=claims.get("id").toString();
        Integer teacherId=Integer.parseInt(id);

        Integer courseId=courseService.getCourseIdByBeginTimeAndTeacherId(week,weekday,section,teacherId);
        return Result.success(courseId);
    }
    @GetMapping("teacherGetCourse/{teacherName}/{week}/{weekday}/{section}")
    public Result GetCourseByTeacherNameAndWeekAndWeekdayAndSection(@PathVariable String teacherName,@PathVariable String week,@PathVariable String  weekday,@PathVariable String section){

        Integer teacherId=teacherService.getIdByTeacherName(teacherName);

        Integer courseId=courseService.getCourseIdByBeginTimeAndTeacherId(week,weekday,section,teacherId);
        return Result.success(courseId);
    }



}
