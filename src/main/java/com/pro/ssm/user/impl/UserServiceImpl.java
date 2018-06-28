package com.pro.ssm.user.impl;

import com.pro.ssm.enumcase.CodeType;
import com.pro.ssm.model.Admin;
import com.pro.ssm.model.Student;
import com.pro.ssm.model.Teacher;
import com.pro.ssm.service.AdminService;
import com.pro.ssm.service.StudentService;
import com.pro.ssm.service.TeacherService;
import com.pro.ssm.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    AdminService adminService;
    @Resource
    TeacherService teacherService;
    @Resource
    StudentService studentService;

    public Map<String, Object> change_password(String userid, String role, String oldpsd, String newpsd) {
        Map<String, Object> res;
        res = userCheck(userid,oldpsd,role);
        if(!res.get("code").equals(CodeType.SUCCESS)){
            //密码校验不成功直接返回错误信息
            return res;
        }
        //更改密码
        do{
            if(role == "teacher"){
                teacherService.changePsd(userid,newpsd);
                break;
            }
            if(role == "student"){
                studentService.changePsd(userid,newpsd);
                break;
            }
            if(role == "admin"){
                adminService.changePsd(userid,newpsd);
                break;
            }
        }while(false);
        res.put("msg","ChangePsdSuccess");
        return res;
    }

    public Map<String, Object> userCheck(String userid, String password, String role) {
        Map<String,Object> res = new HashMap<String, Object>();
        //检查是否存在于数据库
        res.put("data", null);
        do{
            if(role.equals("admin")){
                Admin tmp = adminService.getUserById(userid);
                if(tmp == null){
                    res.put("code", CodeType.ERROR);
                    res.put("msg", "不存在用户名");
                }
                else if(password.equals(tmp.getPassword())){
                    res.put("code", CodeType.SUCCESS);
                    res.put("msg", "成功");
                }
                else{
                    res.put("code", CodeType.ERROR);
                    res.put("msg", "错误的密码");
                }
                break;
            }
            if(role.equals("student")){
                Student tmp = studentService.getUserById(userid);
                if(tmp == null){
                    res.put("code", 1000);
                    res.put("msg", "不存在用户名");
                }
                else if(password.equals(tmp.getPassword())){
                    res.put("code", CodeType.SUCCESS);
                    res.put("msg", "成功");
                }
                else{
                    res.put("code", CodeType.ERROR);
                    res.put("msg", "错误的密码");
                }
                break;
            }
            if(role.equals("teacher")){
                Teacher tmp = teacherService.getUserById(userid);
                if(tmp == null){
                    res.put("code", 1000);
                    res.put("msg", "不存在用户名");
                }
                else if(password.equals(tmp.getPassword())){
                    res.put("code", CodeType.SUCCESS);
                    res.put("msg", "成功");
                }
                else{
                    res.put("code", CodeType.ERROR);
                    res.put("msg", "错误的密码");
                }
                break;
            }
            res.put("code", 1000);
            res.put("msg", "错误的用户类型");
        }while(false);
        return res;
    }
}
