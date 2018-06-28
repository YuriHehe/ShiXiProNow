package com.pro.ssm.service.impl;

import com.pro.ssm.dao.TeacherMapper;
import com.pro.ssm.model.Teacher;
import com.pro.ssm.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {
    @Resource
    TeacherMapper teacherDao;

    public Teacher getUserById(String userid) {
        Teacher tea = teacherDao.selectByPrimaryKey(userid);
        return tea;
    }

    public void changePsd(String userId, String psd) {
        Teacher tmp = teacherDao.selectByPrimaryKey(userId);
        tmp.setPassword(psd);
        teacherDao.updateByPrimaryKeySelective(tmp);
    }

    public int countNum() {
        return teacherDao.selectNum();
    }

    public boolean insertUser(Teacher tea) {
        if(teacherDao.selectByPrimaryKey(tea.getTid()) != null)return false;
        teacherDao.insertSelective(tea);
        return true;
    }

    public boolean updateUser(Teacher tea) {
        if(teacherDao.selectByPrimaryKey(tea.getTid()) == null)return false;
        teacherDao.updateByPrimaryKeySelective(tea);
        return true;
    }

    public boolean delUserById(String userid) {
        if(teacherDao.selectByPrimaryKey(userid) == null)return false;
        teacherDao.deleteByPrimaryKey(userid);
        return true;
    }

    public List<Teacher> selectBySearch(String key) {
        return teacherDao.selectBySearch(key);
    }

    public List<Teacher> selectSome(int st, int n) {
        return teacherDao.selectSome(st,n);
    }

    public List<Teacher> selectAll() {
        return teacherDao.selectAll();
    }
}
