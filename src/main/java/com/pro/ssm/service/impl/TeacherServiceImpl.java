package com.pro.ssm.service.impl;

import com.pro.ssm.dao.TeacherMapper;
import com.pro.ssm.model.Teacher;
import com.pro.ssm.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {
    @Resource
    TeacherMapper teacherDao;

    public Teacher getUserById(String userid) {
        Teacher tea = teacherDao.selectByPrimaryKey(userid);
        return tea;
    }
}
