package com.pro.ssm.dao;

import com.pro.ssm.model.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    int deleteByPrimaryKey(String sid);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(String sid);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    int selectNum();

    List<Student> selectSome(@Param("start_id")int start_id, @Param("num")int num);

    List<Student> selectBySearch(@Param("key")String key);
}