package com.pro.ssm.service;

import com.pro.ssm.model.Student;

public interface StudentService {
    Student getUserById(String userid);

    void changePsd(String userId, String psd);
}
