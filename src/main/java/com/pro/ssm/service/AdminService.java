package com.pro.ssm.service;

import com.pro.ssm.model.Admin;

public interface AdminService {
    Admin getUserById(String userId);

    void changePsd(String userId, String psd);
}
