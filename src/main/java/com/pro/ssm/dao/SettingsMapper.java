package com.pro.ssm.dao;

import com.pro.ssm.model.Settings;

public interface SettingsMapper {
    int deleteByPrimaryKey(String skey);

    int insert(Settings record);

    int insertSelective(Settings record);

    Settings selectByPrimaryKey(String skey);

    int updateByPrimaryKeySelective(Settings record);

    int updateByPrimaryKey(Settings record);
}