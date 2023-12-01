//package com.itheima.service;
//
//import com.github.pagehelper.PageHelper;
//import com.itheima.mapper.EmpMapper;
//import com.itheima.pojo.Emp;
//import com.itheima.pojo.PageBean;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Slf4j
//@Service
//
//public class EmpServiceImpl implements EmpService{
//    @Autowired
//    private EmpMapper empMapper;
//
//    @Override
//    public PageBean page(Integer page, Integer pageSize) {
//        //记录总记录数
//        Long count= empMapper.count();
//        //获取分页查询结果列表
//        Integer start=(page-1)*pageSize;//计算起始索引，公式：(页码-1）*页大小
//        List<Emp> empList=empMapper.list(start,pageSize);
//        //封装PageBean对象
//        PageBean pageBean=new PageBean(count,empList);
//
//
//        return pageBean;
//    }
//    @Override
//    public void deleteByIds(List<Integer> ids){
//        empMapper.deleteByIds(ids);
//    }
//
//    @Override
//    public void addNewEmp(Emp emp) {
//        emp.setCreatTime(LocalDateTime.now());
//        emp.setUpdateTime(LocalDateTime.now());
//        empMapper.addNewEmp(emp);
//    }
//
//    @Override
//    public void modifyEmp(Emp emp) {
//        emp.setCreatTime(LocalDateTime.now());
//        emp.setUpdateTime(LocalDateTime.now());
//        empMapper.modifyEmp(emp);
//    }
//
//    @Override
//    public Emp login(Emp emp) {
//        //调用dao层功能：登录
//        Emp loginEmp=empMapper.getByUsernameAndPassword(emp);
//        //返回查询结果Controller
//        return loginEmp;
//    }
//}
