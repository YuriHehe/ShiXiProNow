package com.pro.ssm.service.impl;

import com.pro.ssm.dao.StudentMapper;
import com.pro.ssm.model.Student;
import com.pro.ssm.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper stuDao;

    public Student getUserById(String userid) {
        Student stu = stuDao.selectByPrimaryKey(userid);
        return stu;
    }

    public void changePsd(String userId, String psd) {
        Student tmp = stuDao.selectByPrimaryKey(userId);
        tmp.setPassword(psd);
        stuDao.updateByPrimaryKey(tmp);
    }

    public int countStudentNum() {
        return 0;
    }
}
