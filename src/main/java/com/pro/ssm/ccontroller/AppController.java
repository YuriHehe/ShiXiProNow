package com.pro.ssm.ccontroller;

import com.pro.ssm.model.Admin;
import com.pro.ssm.model.Student;
import com.pro.ssm.model.Teacher;
import com.pro.ssm.service.AdminService;
import com.pro.ssm.service.StudentService;

import com.pro.ssm.service.TeacherService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class AppController {

    private Logger log = Logger.getLogger(AppController.class);

    @Resource(name = "adminService")
    private AdminService adminService;
    @Resource(name = "studentService")
    private StudentService studentService;
    @Resource(name = "teacherService")
    private TeacherService teacherService;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String,Object> login(HttpServletRequest request, Model model){
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        Map<String,Object> res = new HashMap<String, Object>();
        res.put("code", 1000);
        res.put("msg", "错误的用户类型");
        res.put("data", null);
        //检查是否存在于数据库
        do{
            if(role.equals("admin")){
                Admin tmp = adminService.getUserById(userid);
                if(tmp == null){
                    res.put("code", 1000);
                    res.put("msg", "不存在用户名");
                }
                else if(password.equals(tmp.getPassword())){
                    res.put("code", 200);
                    res.put("msg", "成功");
                }
                else{
                    res.put("code", 1000);
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
                    res.put("code", 200);
                    res.put("msg", "成功");
                }
                else{
                    res.put("code", 1000);
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
                    res.put("code", 200);
                    res.put("msg", "成功");
                }
                else{
                    res.put("code", 1000);
                    res.put("msg", "错误的密码");
                }
                break;
            }
        }while(false);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public Map<String,Object> logout(HttpServletRequest request, Model model){
        Map<String,Object> res = new HashMap<String, Object>();
        res.put("msg","?");
        return res;
    }
}
