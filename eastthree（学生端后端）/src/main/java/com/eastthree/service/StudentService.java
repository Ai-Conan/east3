package com.eastthree.service;

import com.eastthree.pojo.Student;
import org.springframework.stereotype.Service;


@Service
public interface StudentService {
    public Student login(Student student);
    public Student getById(Integer id);


    public Student getByStudentNo(String studentNo);
}
