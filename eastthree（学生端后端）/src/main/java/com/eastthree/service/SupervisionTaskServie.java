package com.eastthree.service;

import org.springframework.stereotype.Service;
import com.eastthree.pojo.PageBean;
@Service
public interface SupervisionTaskServie {
    PageBean page(Integer page, Integer pageSize);
}
