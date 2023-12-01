package com.project.service;

import com.project.pojo.PageBean;
import com.project.pojo.Teacher;
import com.project.pojo.kecheng;

import java.util.List;
import java.util.Map;

/**
 * 员工管理
 */
public interface CourseService {
    /**
     * 分页查询
     * @param page
     * @return
     */
    PageBean page(String courseName, Integer page, Integer no, Integer clas_s);
//
//    /**
//     * 批量删除
//     * @param ids
//     */
    void delete(String courseName,Integer no,Integer clas_s);
//
//    /**
//     * 新增员工
//     * @param emp
//     */
//    void save(Emp emp);
//
//    /**
//     * 根据ID查询员工
//     * @param id
//     * @return
//     */
//    Emp getById(Integer id);
//
//    /**
//     * 更新员工
//     * @param emp
//     */


    List<Map<String, Object>> selectAllCourse(Integer no);

    List<Map<String, Object>> selectCourse(Integer no, String courseName);

    List<String> selectClass(String courseName, Integer no);

    List<kecheng> courseinfor(String courseName, Integer no,  Integer clas_s);

    Teacher login(Teacher teacher);
}
