package com.pro.ssm.dao;

import com.pro.ssm.model.Departmet;

import java.util.List;

public interface DepartmetMapper {
    int deleteByPrimaryKey(Integer did);

    int insert(Departmet record);

    int insertSelective(Departmet record);

    Departmet selectByPrimaryKey(Integer did);

    int updateByPrimaryKeySelective(Departmet record);

    int updateByPrimaryKey(Departmet record);

    List<Departmet> selectAll();
}