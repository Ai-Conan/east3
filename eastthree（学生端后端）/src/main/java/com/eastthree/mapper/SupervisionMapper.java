package com.eastthree.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SupervisionMapper {
    public Integer selectSupervisionIdByStudentId(Integer studentId);
}
