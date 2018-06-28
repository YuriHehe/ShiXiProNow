package com.pro.ssm.service;

import com.pro.ssm.model.Student;

public interface StudentService {
    Student getUserById(String userid);

    //更新学生信息,不存在返回false,成功更新返回true
    boolean updateUser(Student stu);

    //不存在该id对应的学生返回false,成功删除返回true
    boolean delUserById(String userid);

    //已存在该id返回false,成功添加返回true
    boolean insertUser(Student stu);

    void changePsd(String userId, String psd);

    int countStudentNum();
}
