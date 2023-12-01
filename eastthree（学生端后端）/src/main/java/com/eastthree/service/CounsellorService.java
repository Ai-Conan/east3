package com.eastthree.service;

import com.eastthree.pojo.Counsellor;
import org.springframework.stereotype.Service;

@Service
public interface CounsellorService {
    public Counsellor login(Counsellor counsellor);
}
