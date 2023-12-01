package com.eastthree.Controller;

import com.eastthree.pojo.JwtUtils;
import com.eastthree.pojo.Result;
import com.eastthree.pojo.Student;
import com.eastthree.service.StudentService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/studentInformation")
    public Result getById(@RequestHeader String token){
        //设置学生id
        Claims claims=JwtUtils.parseJwt(token);
        String id=claims.get("id").toString();
        Integer StudentId=Integer.parseInt(id);
        Student result=studentService.getById(StudentId);
        //
        return Result.success(result);
    }
    @GetMapping("/getStudentIdByStudentNo/{studentNo}")
    public Result getIdByNo(@PathVariable String studentNo){
        Student student=studentService.getByStudentNo(studentNo);
        Integer studentId=student.getId();
        return Result.success(studentId);
    }
}
