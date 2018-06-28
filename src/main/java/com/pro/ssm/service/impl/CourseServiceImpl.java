package com.pro.ssm.service.impl;

import com.pro.ssm.dao.CourseMapper;
import com.pro.ssm.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CourseServiceImpl implements CourseService {
    @Resource
    CourseMapper courseDao;

    public int countNum() {
        return courseDao.selectNum();
    }
}
