package com.pro.ssm.user.impl;

import com.pro.ssm.util.Msg;
import com.pro.ssm.model.Admin;
import com.pro.ssm.model.Student;
import com.pro.ssm.model.Teacher;
import com.pro.ssm.service.AdminService;
import com.pro.ssm.service.StudentService;
import com.pro.ssm.service.TeacherService;
import com.pro.ssm.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        res = userCheck(userid, oldpsd, role);
        if (!res.get("code").equals(Msg.CodeType.SUCCESS)) {
            //密码校验不成功直接返回错误信息
            return res;
        }
        //更改密码
        do {
            if (role.equals("teacher")) {
                teacherService.changePsd(userid, newpsd);
                break;
            }
            if (role.equals("student")) {
                studentService.changePsd(userid, newpsd);
                break;
            }
            if (role.equals("admin")) {
                adminService.changePsd(userid, newpsd);
                break;
            }
            return Msg.Error("错误的用户类型");
        } while (false);
        return Msg.Success("密码修改成功",null);
    }

    public Map<String, Object> userCheck(String userid, String password, String role) {
        if (role.equals("admin")) {
            Admin tmp = adminService.getUserById(userid);
            if (tmp == null) {
                return Msg.Error("不存在用户名");
            } else if (password.equals(tmp.getPassword())) {
                return Msg.Success("成功", null);
            } else {
                return Msg.Error("错误密码");
            }
        }
        if (role.equals("student")) {
            Student tmp = studentService.getUserById(userid);
            if (tmp == null) {
                return Msg.Error("不存在用户名");
            } else if (password.equals(tmp.getPassword())) {
                return Msg.Success("成功", null);
            } else {
                return Msg.Error("错误密码");
            }
        }
        if (role.equals("teacher")) {
            Teacher tmp = teacherService.getUserById(userid);
            if (tmp == null) {
                return Msg.Error("不存在用户名");
            } else if (password.equals(tmp.getPassword())) {
                return Msg.Success("成功", null);
            } else {
                return Msg.Error("错误密码");
            }
        }
        return Msg.Error("错误的用户类型");
    }
}
