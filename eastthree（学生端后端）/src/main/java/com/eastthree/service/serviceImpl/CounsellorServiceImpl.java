package com.eastthree.service.serviceImpl;

import com.eastthree.mapper.CounsellorMapper;
import com.eastthree.pojo.Counsellor;
import com.eastthree.service.CounsellorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class})
public class CounsellorServiceImpl implements CounsellorService {
    @Autowired
    private CounsellorMapper counsellorMapper;

    @Override
    public Counsellor login(Counsellor counsellor) {
        //调用dao层功能：登录
        Counsellor loginStudent= counsellorMapper.getByUsernameAndPassword(counsellor);
        //返回查询结果Controller
        return loginStudent;
    }
}