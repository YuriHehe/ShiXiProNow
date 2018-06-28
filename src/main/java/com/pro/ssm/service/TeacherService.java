package com.pro.ssm.service;

import com.pro.ssm.model.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher getUserById(String userid);

    void changePsd(String userId, String psd);

    int countNum();

    //更新老师信息,不存在返回false,成功更新返回true
    boolean updateUser(Teacher tea);

    //不存在该id对应的老师返回false,成功删除返回true
    boolean delUserById(String userid);

    //已存在该id返回false,成功添加返回true
    boolean insertUser(Teacher tea);

    List<Teacher> selectAll();

    List<Teacher> selectSome(int st,int n);

    List<Teacher> selectBySearch(String key);
}
