package com.pro.ssm.dao;

import com.pro.ssm.model.Departmet;

public interface DepartmetMapper {
    int deleteByPrimaryKey(Integer did);

    int insert(Departmet record);

    int insertSelective(Departmet record);

    Departmet selectByPrimaryKey(Integer did);

    int updateByPrimaryKeySelective(Departmet record);

    int updateByPrimaryKey(Departmet record);
}