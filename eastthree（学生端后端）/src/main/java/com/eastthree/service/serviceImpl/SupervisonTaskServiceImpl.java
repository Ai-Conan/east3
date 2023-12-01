package com.eastthree.service.serviceImpl;

import com.eastthree.mapper.CourseMapper;
import com.eastthree.mapper.SupervisionTaskMapper;
import com.eastthree.pojo.Course;
import com.eastthree.pojo.PageBean;
import com.eastthree.pojo.resultPojo.ResultTask;
import com.eastthree.service.SupervisionTaskServie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Slf4j
@Transactional(rollbackFor = {Exception.class})
public class SupervisonTaskServiceImpl implements SupervisionTaskServie {
    @Autowired
    SupervisionTaskMapper supervisionTaskMapper;
    @Autowired
    CourseMapper courseMapper;
    @Override
    public PageBean page(Integer page, Integer pageSize) {
        //记录总记录数
        Long count= supervisionTaskMapper.count();
       //获取分页查询结果列表
        Integer start=(page-1)*pageSize;//计算起始索引，公式：(页码-1）*页大小
        List<ResultTask> resultTaskList=supervisionTaskMapper.list(start,pageSize);
       for(ResultTask item:resultTaskList){
           Course target=courseMapper.getById(item.getCourseId());
           item.setCourseName(target.getCourseName());
           item.setBeginTime(target.getBeginTime());
       }

        //封装PageBean对象
        PageBean pageBean=new PageBean(count,resultTaskList);
        return pageBean;
    }
}
