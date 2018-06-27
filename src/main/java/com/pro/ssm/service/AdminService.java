package com.pro.ssm.service;

import com.pro.ssm.model.Admin;

import java.util.List;

public interface AdminService {

    List<Admin> getAllUser();

    Admin getUserById(String userId);
}
