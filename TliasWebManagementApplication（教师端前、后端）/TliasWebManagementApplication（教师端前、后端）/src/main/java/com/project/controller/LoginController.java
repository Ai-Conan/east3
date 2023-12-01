package com.project.controller;

import com.project.pojo.JwtUtils;
import com.project.pojo.Result;
import com.project.pojo.Teacher;
import com.project.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public Result login(@RequestBody(required = true) Teacher teacher) {

        if (teacher == null){
            throw new RuntimeException("post请求体body不能为空！");
        }

        log.info("登录: {}", teacher);
        Teacher e = courseService.login(teacher);
        // 登录成功，生成令牌，下发令牌

         if(e!=null){

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getNo());
            claims.put("name", e.getPassword());
            String jwt = JwtUtils.generateJwt(claims); // jwt包含了当前登录的员工信息
            return Result.success(jwt);
         }

        // 登录失败，返回错误信息
         return Result.error("用户名或密码错误");
    }
}
