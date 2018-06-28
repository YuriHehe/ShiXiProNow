package com.pro.ssm.dao;

import com.pro.ssm.model.StuCls;
import com.pro.ssm.model.StuClsKey;

public interface StuClsMapper {
    int deleteByPrimaryKey(StuClsKey key);

    int insert(StuCls record);

    int insertSelective(StuCls record);

    StuCls selectByPrimaryKey(StuClsKey key);

    int updateByPrimaryKeySelective(StuCls record);

    int updateByPrimaryKey(StuCls record);
}