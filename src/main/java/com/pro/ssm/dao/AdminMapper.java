package com.pro.ssm.dao;

import com.pro.ssm.model.Admin;

public interface AdminMapper {
    int deleteByPrimaryKey(String aid);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(String aid);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}