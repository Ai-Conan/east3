package com.eastthree.service.serviceImpl;

import com.eastthree.mapper.StudentMapper;
import com.eastthree.pojo.Student;
import com.eastthree.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class})
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student login(Student student) {
        //调用dao层功能：登录
        Student loginStudent=studentMapper.getByUsernameAndPassword(student);
        //返回查询结果Controller
        return loginStudent;
    }

    @Override
    public Student getById(Integer id) {
        Student result=studentMapper.getById(id);
        return result;
    }

    @Override
    public Student getByStudentNo(String studentNo) {
        Student student=studentMapper.getByNo(studentNo);
        return student;
    }
}
