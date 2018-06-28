package com.pro.ssm.service;

import com.pro.ssm.model.Teacher;

public interface TeacherService {
    Teacher getUserById(String userid);

    void changePsd(String userId, String psd);

    int countNum();
}
