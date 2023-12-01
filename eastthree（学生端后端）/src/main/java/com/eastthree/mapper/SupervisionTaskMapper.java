package com.eastthree.mapper;

import com.eastthree.pojo.resultPojo.ResultTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SupervisionTaskMapper {
    //获取总记录数,表里总的有几行数据
    public Long count() ;
    //获取当前页的结果列表
    public List<ResultTask> list(Integer start, Integer pageSize);
    public List<Integer> getCourselistByStudentId(Integer studentId);
}
