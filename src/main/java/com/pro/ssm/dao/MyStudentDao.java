package com.pro.ssm.dao;

import com.pro.ssm.model.custom.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyStudentDao {
    List<ClassTable> getClasstabeByStuid(@Param("stuid") String stuid, @Param("week") int week);
    List<CourseInfo> getStuCourseByStuid(@Param("stuid") String stuid);
    List<CourseInfo> getStuAvailableCourseByStuid(@Param("stuid") String stuid);
    List<ClsInfo> getClsByTeacheName(@Param("tname") String tname);
    List<MessageInfo> getStuMessage(@Param("stuid") String stuid);
    List<GradeInfo> getStuGrade(@Param("stuid") String stuid);
}
