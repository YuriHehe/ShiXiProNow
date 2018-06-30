package com.pro.ssm.aop;

import com.pro.ssm.util.Msg;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.JoinTable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Aspect
@Component
public class LoginAspect {
    @Pointcut(value = "execution(* com.pro.ssm.controller.AdminController.*(..))")
    public void loginCheckAdmin(){}

    @Pointcut(value = "execution(* com.pro.ssm.controller.StuController.*(..))")
    public void loginCheckStudent(){}

    @Around("loginCheckAdmin()")
    public Map<String,Object> loginAdmin(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        Object login = request.getSession().getAttribute("login");
        if (login == null) {
            return Msg.Error("权限错误");
        }
        String sl = login.toString();
        if(!sl.equals("1")){
            return Msg.Error("权限错误");
        }
        String userid = request.getSession().getAttribute("userid").toString();
        String role = request.getSession().getAttribute("role").toString();
        if(!role.equals("student")){
            return Msg.Error("权限错误");
        }
        return (Map<String,Object>)joinPoint.proceed();
    }
    @Around("loginCheckStudent()")
    public Map<String,Object> loginStu(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        Object login = request.getSession().getAttribute("login");
        if (login == null) {
            return Msg.Error("权限错误");
        }
        String sl = login.toString();
        if(!sl.equals("1")){
            return Msg.Error("权限错误");
        }
        String userid = request.getSession().getAttribute("userid").toString();
        String role = request.getSession().getAttribute("role").toString();
        if(!role.equals("student")){
            return Msg.Error("权限错误");
        }
        return (Map<String,Object>)joinPoint.proceed();
    }
}
