package com.eastthree.Controller;
import com.eastthree.pojo.*;
import com.eastthree.pojo.Student;
import com.eastthree.service.CounsellorService;
import com.eastthree.service.StudentService;
import com.eastthree.service.SupervisionService;
import com.eastthree.service.TeacherService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CounsellorService counsellorService;
    @Autowired
    private SupervisionService supervisionService;
    @PostMapping("/loginStudent")
    public Result loginStudent(@RequestBody Student student){
        Student loginStudent=studentService.login(student);
        //判断：登录用户是否存在
        if(loginStudent!=null){
            //自定义信息
            Map<String,Object> claims=new HashMap<>();
            claims.put("id",loginStudent.getId());
            claims.put("no",loginStudent.getNo());
            claims.put("name",loginStudent.getName());
            //使用JWT工具类，生成身份令牌
            String token= JwtUtils.generateJwt(claims);
            return Result.success(token);
        }
        return Result.error("用户名或密码错误");
    }
    //判断学生是否是督导
    @GetMapping("/isSupervisor")
    public Result IsSupervisor(@RequestHeader String token){
        //设置学生id
        Claims claims= JwtUtils.parseJwt(token);
        String studentIdString=claims.get("id").toString();
        Integer studentId=Integer.parseInt(studentIdString);
        Integer supervisionId=supervisionService.selectSupervisionIdByStudentId(studentId);
        if(supervisionId!=null)
            return Result.success(1);
        else
            return Result.success(0);
    }
    @PostMapping("/loginTeacher")
    public Result loginTeacher(@RequestBody Teacher teacher){
        Teacher loginTeacher=teacherService.login(teacher);
        //判断：登录用户是否存在
        if(loginTeacher!=null){
            //自定义信息
            Map<String,Object> claims=new HashMap<>();
            claims.put("id",loginTeacher.getId());
            claims.put("no",loginTeacher.getNo());
            claims.put("name",loginTeacher.getName());
            //使用JWT工具类，生成身份令牌
            String token= JwtUtils.generateJwt(claims);
            return Result.success(token);
        }
        return Result.error("用户名或密码错误");
    }
    @PostMapping("/loginCounsellor")
    public Result loginTeacher(@RequestBody Counsellor counsellor){
        Counsellor loginCounsellor=counsellorService.login(counsellor);
        //判断：登录用户是否存在
        if(loginCounsellor!=null){
            //自定义信息
            Map<String,Object> claims=new HashMap<>();
            claims.put("id",loginCounsellor.getId());
            claims.put("no",loginCounsellor.getNo());
            claims.put("name",loginCounsellor.getName());
            //使用JWT工具类，生成身份令牌
            String token= JwtUtils.generateJwt(claims);
            return Result.success(token);
        }
        return Result.error("用户名或密码错误");
    }
}
