package com.project.controller;

import com.project.pojo.Course;
import com.project.pojo.Result;
import com.project.pojo.Stu;
import com.project.service.scheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.project.controller.randomWithWeight.getStudentWeight;

/**
 * 部门管理Controller
 */
@Slf4j
@RestController
public class scheduleController {
    @Autowired
    private scheduleService scheduleService;
    @GetMapping("{no}/schedule")
    public Result list(@PathVariable Integer no){
        log.info("查询当周课表");
        List<Course> List =  scheduleService.list(no);
        return Result.success(List);
    }
    @GetMapping("{no}/scheduleWhat/ToBeOrNotToBe")
    public Result list2(@PathVariable Integer no,@RequestParam Integer week){
        log.info("查询某周课表");

        List<Course> List =  scheduleService.list2(no,week);
        return Result.success(List);
    }

    @GetMapping("{no}/dianming")
    public Result luck(@PathVariable Integer no){
        log.info("点名");
        ArrayList<Stu> studentList=scheduleService.choice(no);
        // 计算每位同学的占比范围
        double[] weightArr = getStudentWeight(studentList);

        // 随机抽取幸运儿
        Random rander = new Random();
        double rand = rander.nextDouble();
        // 二分查找幸运儿所在区间
        int i =  Arrays.binarySearch(weightArr,rand);
        int luckyIndex = -1;
        if(i >= 0)
            luckyIndex = i;
        else
            luckyIndex = -i -1;
        return Result.success(studentList.get(luckyIndex).getName());
    }
}
