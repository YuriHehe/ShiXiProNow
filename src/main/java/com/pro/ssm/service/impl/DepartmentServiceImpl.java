package com.pro.ssm.service.impl;

import com.pro.ssm.dao.DepartmetMapper;
import com.pro.ssm.model.Departmet;
import com.pro.ssm.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    DepartmetMapper departmetDao;

    public List<Departmet> selectAll() {
        return departmetDao.selectAll();
    }
}
