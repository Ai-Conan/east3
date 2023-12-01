package com.eastthree.mapper;
import com.eastthree.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    Student getByUsernameAndPassword(Student student);
    Student getById(Integer id);
    //通过专业班级年级获取学生id
    List<Student> getByGradeAndMajorAndClass(String grade, String major, String Class);
    List<Student> getByCollege(String major);

    Student getByNo(String studentNo);
}
