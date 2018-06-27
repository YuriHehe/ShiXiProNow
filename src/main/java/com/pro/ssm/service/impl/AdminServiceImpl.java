package com.pro.ssm.service.impl;

import com.pro.ssm.model.Admin;
import com.pro.ssm.dao.AdminMapper;
import com.pro.ssm.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminDao;

    public Admin getUserById(String userId) {
        return adminDao.selectByPrimaryKey(userId);
    }

    public List<Admin> getAllUser() {
        List<Admin> tmp = new ArrayList<Admin>();
        Admin tmp2 = getUserById("admin");
        tmp.add(tmp2);
        return tmp;
    }
}
