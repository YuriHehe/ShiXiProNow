package com.pro.ssm.service.impl;

import com.pro.ssm.model.Admin;
import com.pro.ssm.dao.AdminMapper;
import com.pro.ssm.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminDao;

    public Admin getUserById(String userId) {
        return adminDao.selectByPrimaryKey(userId);
    }
}
