package com.pro.ssm.dao;

import com.pro.ssm.model.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {
    int deleteByPrimaryKey(String tid);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(String tid);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);

    int selectNum();

    List<Teacher> selectAll();

    List<Teacher> selectSome(@Param("start_id")int start_id,@Param("num")int num);

    List<Teacher> selectBySearch(@Param("key")String key);
}