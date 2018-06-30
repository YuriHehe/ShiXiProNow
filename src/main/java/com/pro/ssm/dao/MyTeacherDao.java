package com.pro.ssm.dao;

import com.pro.ssm.model.custom.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyTeacherDao {
    List<CourseInfo> searchCourseList(@Param("teacher_id") String teacher_id, @Param("key") String key);

    List<ClassTable> getCourseList(@Param("teacher_id") String teacher_id);

    List<CourseDetailInfo> getCourseDetail(@Param("cid") Integer cid);

    List<ClsInfo> getTeacherClass(@Param("tid") String tid, @Param("start") int start, @Param("n") int n);

    List<GradeBase> getClassGradeList(@Param("clsid") Integer clsid);
}
