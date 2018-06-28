package com.pro.ssm.service;

import com.pro.ssm.model.Course;

public interface CourseService {
    int countNum();

    //更新课程信息,不存在返回false,成功更新返回true
    boolean updateCs(Course cs);

    //不存在该id对应的course返回false,成功删除返回true
    boolean delCsById(int userid);

    //已存在该id返回false,成功添加返回true
    boolean insertCs(Course cs);
}
