package com.eastthree.service;

import org.springframework.stereotype.Service;

@Service
public interface SupervisionService {

    public Integer selectSupervisionIdByStudentId(Integer studentId);
}
