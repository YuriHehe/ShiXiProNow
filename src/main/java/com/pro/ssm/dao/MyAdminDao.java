package com.pro.ssm.dao;

import com.pro.ssm.model.custom.CourseDetailInfo;
import com.pro.ssm.model.custom.CourseInfo;
import com.pro.ssm.model.custom.DeptCourseInfo;
import com.pro.ssm.model.custom.MessageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyAdminDao {
    List<DeptCourseInfo> deptCourseInfo();

    List<CourseInfo> searchCourseList(@Param("key") String key);

    List<CourseInfo> getCourseList(@Param("start") int start, @Param("n") int n);

    List<CourseDetailInfo> getDeptCourseGrade(@Param("did") int did);

    List<MessageInfo>  getMessage();
}
