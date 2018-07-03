package com.pro.ssm.aop;

import com.pro.ssm.dao.SettingsMapper;
import com.pro.ssm.util.Msg;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class SystemStateAspect {
    @Pointcut(value = "execution(* com.pro.ssm.controller.AdminController.addCourse(..)) ||" +
            "execution(* com.pro.ssm.controller.AdminController.editCourse(..)) ||" +
            "execution(* com.pro.ssm.controller.AdminController.delCourse(..)) || " +
            "execution(* com.pro.ssm.controller.AdminController.addClass(..)) || " +
            "execution(* com.pro.ssm.controller.AdminController.editClass(..)) || " +
            "execution(* com.pro.ssm.controller.AdminController.delClass(..)) || " +
            "execution(* com.pro.ssm.controller.AdminController.addSchedule(..)) || " +
            "execution(* com.pro.ssm.controller.AdminController.editSchedule(..)) || " +
            "execution(* com.pro.ssm.controller.AdminController.delSchedule(..))")
    public void checkAdminManagementState() {
    }

    @Pointcut(value = "execution(* com.pro.ssm.controller.TeacherController.class_add(..)) ||" +
            "execution(* com.pro.ssm.controller.TeacherController.class_del(..))")
    public void checkTeacherSelCourseState() {
    }

    @Pointcut(value = "execution(* com.pro.ssm.controller.StuController.commit_class(..)) ||" +
            "execution(* com.pro.ssm.controller.StuController.del_class(..))")
    public void checkStuSelCourseState() {
    }

    @Pointcut(value = "execution(* com.pro.ssm.controller.TeacherController.commit_grade(..))")
    public void checkTeacherGradeState() {
    }

    @Resource
    private SettingsMapper settingDao;

    public static String AdminState = "1";
    public static String TeacherCourseState = "2";
    public static String StudentCourseState = "3";
    public static String TeacherGradeState = "4";

    // 管理员课程管理阶段
    @Around("checkAdminManagementState()")
    public Object AroundCheckAdminManagementState(ProceedingJoinPoint joinPoint) throws Throwable {
        if (AdminState.equals(settingDao.selectByPrimaryKey("state").getSvalue()))
            return joinPoint.proceed();
        else
            return Msg.Error("当前时间段不允许该操作");
    }

    //教师选择教学班阶段
    @Around("checkTeacherSelCourseState()")
    public Object AroundCheckTeacherSelCourseState(ProceedingJoinPoint joinPoint) throws Throwable {
        if (TeacherCourseState.equals(settingDao.selectByPrimaryKey("state").getSvalue()))
            return joinPoint.proceed();
        else
            return Msg.Error("当前时间段不允许该操作");
    }

    //学生选课阶段
    @Around("checkStuSelCourseState()")
    public Object AroundCheckStuSelCourseState(ProceedingJoinPoint joinPoint) throws Throwable {
        if (StudentCourseState.equals(settingDao.selectByPrimaryKey("state").getSvalue()))
            return joinPoint.proceed();
        else
            return Msg.Error("当前时间段不允许该操作");
    }

    //教师录入成绩阶段
    @Around("checkTeacherGradeState()")
    public Object AroundCheckTeacherGradeState(ProceedingJoinPoint joinPoint) throws Throwable {
        if (TeacherGradeState.equals(settingDao.selectByPrimaryKey("state").getSvalue()))
            return joinPoint.proceed();
        else
            return Msg.Error("当前时间段不允许该操作");
    }
}
