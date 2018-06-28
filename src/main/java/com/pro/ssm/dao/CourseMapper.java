package com.pro.ssm.dao;

import com.pro.ssm.model.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    int deleteByPrimaryKey(Integer cid);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer cid);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    int selectNum();

    List<Course> selectSome(@Param("start_id")int start_id, @Param("num")int num);

    List<Course> selectBySearch(@Param("key")String key);
}