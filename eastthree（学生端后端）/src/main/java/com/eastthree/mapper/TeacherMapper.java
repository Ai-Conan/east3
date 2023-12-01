package com.eastthree.mapper;
import com.eastthree.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherMapper {
    Teacher getByUsernameAndPassword(Teacher teacher);

    Integer getIdByName(String teacherName);
}