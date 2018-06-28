package com.pro.ssm.dao;

import com.pro.ssm.model.Cls;

public interface ClsMapper {
    int deleteByPrimaryKey(Integer clsid);

    int insert(Cls record);

    int insertSelective(Cls record);

    Cls selectByPrimaryKey(Integer clsid);

    int updateByPrimaryKeySelective(Cls record);

    int updateByPrimaryKey(Cls record);

    int selectNum();
}