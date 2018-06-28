package com.pro.ssm.service.impl;

import com.pro.ssm.dao.StudentMapper;
import com.pro.ssm.model.Student;
import com.pro.ssm.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper stuDao;

    public Student getUserById(String userid) {
        Student stu = stuDao.selectByPrimaryKey(userid);
        return stu;
    }

    public boolean delUserById(String userid){
        Student stu = stuDao.selectByPrimaryKey(userid);
        if(stu == null){
            return false;
        }
        else{
            stuDao.deleteByPrimaryKey(userid);
            return true;
        }
    }

    public void changePsd(String userId, String psd) {
        Student tmp = stuDao.selectByPrimaryKey(userId);
        tmp.setPassword(psd);
        stuDao.updateByPrimaryKey(tmp);
    }

    public int countStudentNum() {
        return stuDao.selectNum();
    }

    public boolean updateUser(Student stu) {
        if(stuDao.selectByPrimaryKey(stu.getSid()) == null)return false;
        stuDao.updateByPrimaryKeySelective(stu);
        return true;
    }

    public boolean insertUser(Student stu) {
        if(stuDao.selectByPrimaryKey(stu.getSid()) != null)return false;
        stuDao.insertSelective(stu);
        return true;
    }

    public List<Student> selectSome(int start_id, int num) {
        return stuDao.selectSome(start_id,num);
    }

    public List<Student> selectBySearch(String key) {
        return stuDao.selectBySearch(key);
    }
}
