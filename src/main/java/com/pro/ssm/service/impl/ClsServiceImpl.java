package com.pro.ssm.service.impl;

import com.pro.ssm.dao.ClsMapper;
import com.pro.ssm.service.ClsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClsServiceImpl implements ClsService {
    @Resource
    ClsMapper clsDao;

    public int countNum() {
        return clsDao.selectNum();
    }
}
