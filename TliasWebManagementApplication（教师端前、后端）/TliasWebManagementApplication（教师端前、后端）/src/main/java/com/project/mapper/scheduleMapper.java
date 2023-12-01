package com.project.mapper;

import com.project.pojo.Course;
import com.project.pojo.Stu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门管理
 */
@Mapper
public interface scheduleMapper {

    @Select("select DISTINCT course.course_name,course.week,course.weekday,course.course_place,course.section\n" +
            "from course,teacher where course.teacherName=teacher.name and teacher.no= #{no} and week=WEEK(DATE(NOW()), 1) - WEEK('2023-08-28', 1)+1;")
    List<Course> list(Integer no);
    

    @Select("SELECT students.name,FORMAT((COUNT(course.id)-COUNT(DISTINCT attendance_result.id)) / COUNT(course.id) * 100, 2) as weight\n" +
            "            FROM students\n" +
            "                     JOIN course ON students.id = course.studentId\n" +
            "                     JOIN teacher ON course.teacherName = teacher.name\n" +
            "                     LEFT JOIN attendance_result ON attendance_result.courseId = course.id AND attendance_result.studentId = students.id\n" +
            "            WHERE teacher.no = #{no}\n" +
            "    GROUP BY students.id, students.name, students.major")

    ArrayList<Stu> choice(Integer no);


    @Select("select DISTINCT course.course_name,course.week,course.weekday,course.course_place,course.section\n" +
            "from course,teacher where course.teacherName=teacher.name and teacher.no= #{no} and week=#{week};")
    List<Course> list2(Integer no, Integer week);
}
