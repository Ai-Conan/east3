package com.project.service.impl;
import java.util.Map;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.mapper.CourseMapper;
import com.project.pojo.PageBean;
import com.project.pojo.Teacher;
import com.project.pojo.kecheng;
import com.project.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    /*@Override
    public PageBean page(Integer page, Integer pageSize) {
        //1. 获取总记录数
        Long count = empMapper.count();

        //2. 获取分页查询结果列表
        Integer start = (page - 1) * pageSize;
        List<Emp> empList = empMapper.page(start, pageSize);

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(count, empList);
        return pageBean;
    }*/


    @Override
    public PageBean page(String courseName, Integer page, Integer no, Integer clas_s) {
        //1. 设置分页参数
        PageHelper.startPage(page, 100);

        //2. 执行查询
        List<kecheng> empList = courseMapper.list(courseName, no, clas_s);
        Page<kecheng> p = (Page<kecheng>) empList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public void delete(String courseName, Integer no, Integer clas_s) {
        courseMapper.delete(courseName, no, clas_s);
    }
//
//    @Override
//    public void save(Emp emp) {
//        emp.setCreateTime(LocalDateTime.now());
//        emp.setUpdateTime(LocalDateTime.now());
//        empMapper.insert(emp);
//    }
//
//    @Override
//    public Emp getById(Integer id) {
//        return empMapper.getById(id);
//    }


    @Override
    public List<Map<String, Object>> selectAllCourse(Integer no) {
        return courseMapper.selectAllCourse(no);
    }

    @Override
    public List<Map<String, Object>> selectCourse(Integer no, String courseName) {
        return courseMapper.selectCourse(no, courseName);
    }

    @Override
    public List<String> selectClass(String courseName, Integer no) {
        return courseMapper.selectClass(courseName, no);
    }


    @Override
    public List<kecheng> courseinfor(String courseName,Integer no,  Integer clas_s) {
        return courseMapper.courseinfor(courseName,no,clas_s);
    }
    @Override
    public Teacher login(Teacher teacher) {
        return courseMapper.getByUsernameAndPassword(teacher);
    }




}
