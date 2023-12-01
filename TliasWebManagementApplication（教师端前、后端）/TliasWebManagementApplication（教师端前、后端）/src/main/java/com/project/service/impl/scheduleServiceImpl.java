package com.project.service.impl;

import com.project.mapper.scheduleMapper;
import com.project.pojo.Course;
import com.project.pojo.Stu;
import com.project.service.scheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class scheduleServiceImpl implements scheduleService {

    @Autowired
    private scheduleMapper scheduleMapper;

    @Override
    public List<Course> list(Integer no) {
        return scheduleMapper.list(no);
    }

    @Override
    public ArrayList<Stu> choice(Integer no) {
        return scheduleMapper.choice(no);
    }

    @Override
    public List<Course> list2(Integer no, Integer week) {
        return scheduleMapper.list2(no,week);
    }


}
