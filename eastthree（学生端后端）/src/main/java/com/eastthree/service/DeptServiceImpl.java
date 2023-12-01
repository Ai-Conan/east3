//package com.itheima.service;
//
//import com.itheima.mapper.DeptMapper;
//import com.itheima.pojo.Dept;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Slf4j
//@Service
//public class DeptServiceImpl implements DeptService{
//    @Autowired
//    private DeptMapper deptMapper;
//    @Override
//    public List<Dept> list(){
//        List<Dept> deptList=deptMapper.list();
//        return deptList;
//    }
//    @Override
//    public void deleteById(Integer id){
//        deptMapper.deleteById(id);
//    }
//    public void AddNewDept( String name){
//        LocalDateTime createtime=LocalDateTime.now();
//        LocalDateTime updatetime=LocalDateTime.now();
//        deptMapper.insertNewDept(name, createtime,updatetime);
//    }
//    public Dept selectById(Integer id){
//        Dept dept=deptMapper.selectById(id);
//
//        return dept;
//    }
//    @Override
//    public void modifyDept(Dept dept){
//        deptMapper.modifyDeptName(dept);
//    }
//
//
//
//}
