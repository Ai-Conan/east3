package com.eastthree.service.serviceImpl;

import com.eastthree.mapper.SupervisionMapper;
import com.eastthree.service.SupervisionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class})
public class SupervisionServiceImpl implements SupervisionService {
    @Autowired
    private SupervisionMapper supervisionMapper;

    @Override
    public Integer selectSupervisionIdByStudentId(Integer studentId) {
        Integer supervisionId=supervisionMapper.selectSupervisionIdByStudentId(studentId);
        return supervisionId;
    }
}
