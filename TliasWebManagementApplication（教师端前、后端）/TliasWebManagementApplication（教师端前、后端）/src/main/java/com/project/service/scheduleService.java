package com.project.service;

import com.project.pojo.Course;
import com.project.pojo.Stu;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门管理
 */
public interface scheduleService {
    /**
     * 查询全部部门数据
     * @return
     */
    List<Course> list(Integer no);


    ArrayList<Stu> choice(Integer no);

    List<Course> list2(Integer no, Integer week);
}
