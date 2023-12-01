package com.eastthree.Controller;

import com.eastthree.pojo.PageBean;
import com.eastthree.pojo.Result;
import com.eastthree.service.SupervisionTaskServie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class SupervisionTaskController {
    @Autowired
    private SupervisionTaskServie supervisionTaskServie;
    @GetMapping("/SupervisionTask")
    private Result getSupervisonTask(@RequestParam(defaultValue = "1")Integer page,
                                 @RequestParam(defaultValue = "5") Integer pageSize){
        //记录日志
        log.info("分页查询，参数：{}，{}",page,pageSize);
        //调用业务层分页查询功能
        PageBean pageBean= supervisionTaskServie.page(page,pageSize);
        //响应
        return Result.success(pageBean);
    }
}
