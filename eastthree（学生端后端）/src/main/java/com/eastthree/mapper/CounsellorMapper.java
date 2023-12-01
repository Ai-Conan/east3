package com.eastthree.mapper;
import com.eastthree.pojo.Counsellor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CounsellorMapper {
    Counsellor getByUsernameAndPassword(Counsellor counsellor);
    Counsellor getById(Integer id);
}
