package com.project.mapper;

import com.project.pojo.Teacher;
import com.project.pojo.kecheng;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 员工管理
 */
@Mapper
public interface CourseMapper {

    /**
     * 查询总记录数
     * @return
     */
    //@Select("select count(*) from emp")
    //public Long count();

    /**
     * 分页查询,获取列表数据
     * @param start
     * @param pageSize
     * @return
     */
    //@Select("select * from emp limit #{start},#{pageSize}")
    //public List<Emp> page(Integer start, Integer pageSize);

    /**
     * 员工信息查询
     * @return
     */
    @Select("SELECT students.no as id, students.name, students.major, COUNT(course.id) AS totalClasses, IFNULL(absentCount, 0) AS absentCount,\n" +
            "       CONCAT(FORMAT(IFNULL((COUNT(course.id)-IFNULL(absentCount, 0)) / COUNT(course.id) * 100, 0), 2), '%') AS absentRate\n" +
            "FROM students\n" +
            "         JOIN course ON students.id = course.studentId\n" +
            "         JOIN teacher ON course.teacherName = teacher.name\n" +
            "         LEFT JOIN (\n" +
            "    SELECT studentId, COUNT(studentId) AS absentCount\n" +
            "    FROM attendance_result\n" +
            "    WHERE courseId IN (SELECT id FROM course WHERE course_name = #{course_name})\n" +
            "and status ='缺勤'\n"+
            "    GROUP BY studentId\n" +
            ") AS ar ON students.id = ar.studentId\n" +
            "WHERE teacher.no = #{no} and students.clas_s=#{clas_s} and course_name=#{course_name}\n" +
            "GROUP BY students.id")
    public List<kecheng> list(String course_name,Integer no, Integer clas_s);


    @Delete("DELETE FROM course\n" +
            "WHERE course.studentId IN (\n" +
            "    SELECT id\n" +
            "    FROM (\n" +
            "             SELECT students.id\n" +
            "             FROM students\n" +
            "                      JOIN course ON students.id = course.studentId\n" +
            "                      JOIN teacher ON course.teacherName = teacher.name\n" +
            "                      LEFT JOIN attendance_result ON attendance_result.courseId = course.id AND attendance_result.studentId = students.id\n" +
            "             WHERE teacher.no = #{no} and students.clas_s=#{clas_s} and course_name=#{course_name}\n" +
            "             GROUP BY students.id, students.name, students.major\n" +
            "         ) AS temp_students\n" +
            ");")
    void delete(String course_name,Integer no,Integer clas_s);


    @Select("SELECT DISTINCT course_name,count(DISTINCT course.studentId) AS count\n" +
            "            FROM course, teacher\n" +
            "            WHERE no=#{no} AND teacherName=name\n" +
            "            group by course_name;")
    List<Map<String, Object>> selectAllCourse(Integer no);

    @Select("SELECT DISTINCT course_name, COUNT(DISTINCT course.studentId) AS count\n" +
            "FROM course, teacher\n" +
            "WHERE no = #{no} AND teacherName = name AND course_name LIKE CONCAT('%', #{course_name}, '%')\n" +
            "GROUP BY course_name")
    List<Map<String, Object>> selectCourse(Integer no, String course_name);

    @Select("SELECT DISTINCT clas_s\n" +
            "FROM course,students,teacher\n" +
            "WHERE teacher.no=#{no} AND  course_name=#{course_name} and course.studentId=students.id and teacherName=teacher.name;\n")
    List<String> selectClass(String course_name, Integer no);

    @Select("SELECT students.no as id, students.name, students.major, COUNT(course.id) AS totalClasses, IFNULL(absentCount, 0) AS absentCount,\n" +
            "       CONCAT(FORMAT(IFNULL((COUNT(course.id)-IFNULL(absentCount, 0)) / COUNT(course.id) * 100, 0), 2), '%') AS absentRate\n" +
            "FROM students\n" +
            "         JOIN course ON students.id = course.studentId\n" +
            "         JOIN teacher ON course.teacherName = teacher.name\n" +
            "         LEFT JOIN (\n" +
            "    SELECT studentId, COUNT(studentId) AS absentCount\n" +
            "    FROM attendance_result\n" +
            "    WHERE courseId IN (SELECT id FROM course WHERE course_name = #{course_name})\n" +
            "    GROUP BY studentId\n" +
            ") AS ar ON students.id = ar.studentId\n" +
            "WHERE teacher.no = #{no} and students.clas_s=#{clas_s} and course_name=#{course_name}\n" +
            "GROUP BY students.id")
    public List<kecheng> courseinfor(String course_name, Integer no,  Integer clas_s);

    @Select("select * from teacher where no = #{no} and password = #{password}")
    Teacher getByUsernameAndPassword(Teacher teacher);
}
