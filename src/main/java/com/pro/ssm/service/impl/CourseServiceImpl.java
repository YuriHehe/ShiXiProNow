package com.pro.ssm.service.impl;

import com.pro.ssm.dao.CourseMapper;
import com.pro.ssm.model.Course;
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

    public boolean insertCs(Course cs) {
        if(courseDao.selectByPrimaryKey(cs.getCid())!=null)return false;
        courseDao.insertSelective(cs);
        return true;
    }

    public boolean delCsById(int userid) {
        if(courseDao.selectByPrimaryKey(userid)==null)return false;
        courseDao.deleteByPrimaryKey(userid);
        return true;
    }

    public boolean updateCs(Course cs) {
        if(courseDao.selectByPrimaryKey(cs.getCid())==null)return false;
        courseDao.updateByPrimaryKeySelective(cs);
        return true;
    }
}
