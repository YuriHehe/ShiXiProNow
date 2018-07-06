package com.pro.ssm.controller;

import com.pro.ssm.model.Admin;
import com.pro.ssm.model.Student;
import com.pro.ssm.model.Teacher;
import com.pro.ssm.service.AdminService;
import com.pro.ssm.service.StudentService;
import com.pro.ssm.service.TeacherService;
import com.pro.ssm.util.ExcelUtil;
import com.pro.ssm.util.MD5Util;
import com.pro.ssm.util.Msg;
import com.pro.ssm.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(maxAge = 3600)
@RequestMapping("/user")
public class AppController {

    private Logger log = Logger.getLogger(AppController.class);

    @Resource
    private UserService userService;

    @Resource
    private AdminService adminService;

    @Resource
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;

    @ResponseBody
    @RequestMapping(value = "/testDownload", method = RequestMethod.GET)
    public Map<String, Object> testDownload(HttpServletResponse response) throws Exception {
        List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; ++i) {
            Map<String, Object> tt = new HashMap<String, Object>();
            tt.put("a", i);
            tt.put("b", i + 1);
            tmp.add(tt);
        }
//        ExcelUtil.writeXls("temp", tmp, response);
        return Msg.Success();
    }

    @ResponseBody
    @RequestMapping(value = "/testChangePsd", method = RequestMethod.GET)
    public Map<String, Object> testChangePsd(HttpServletRequest request, Model model) {
        String userid = request.getSession().getAttribute("userid").toString();
        String role = request.getSession().getAttribute("role").toString();
        String oldpsd = request.getParameter("old");
        String newpsd = request.getParameter("new");
        return userService.change_password(userid, role, oldpsd, newpsd);
    }

    @ResponseBody
    @RequestMapping(value = "/testLogin", method = RequestMethod.GET)
    public Map<String, Object> testLogin(HttpServletRequest request, @RequestParam("userid") String userid, @RequestParam("role") String role, @RequestParam("password") String password) {
        //检查是否存在于数据库
        Map<String, Object> res = userService.userCheck(userid, password, role);
        if (res.get("code").equals(200)) {
            request.getSession().setAttribute("login", "1");
            request.getSession().setAttribute("userid", userid);
            request.getSession().setAttribute("role", role);
            return Msg.Success("登陆成功");
        }
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(HttpServletRequest request, Model model) {
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        password = MD5Util.crypt(password);
        Map<String, Object> res;
        String username = "";
        //检查是否存在于数据库
        if (role.equals("admin")) {
            Admin tmp = adminService.getUserById(userid);
            if (tmp == null) {
                res = Msg.Error("不存在用户名");
            } else if (password.equals(tmp.getPassword())) {
                res = Msg.Success("成功", null);
                username = "管理员";
            } else {
                res = Msg.Error("错误密码");
            }
        } else if (role.equals("student")) {
            Student tmp = studentService.getUserById(userid);
            if (tmp == null) {
                res = Msg.Error("不存在用户名");
            } else if (password.equals(tmp.getPassword())) {
                res = Msg.Success("成功", null);
                username = tmp.getSname();
            } else {
                res = Msg.Error("错误密码");
            }
        } else if (role.equals("teacher")) {
            Teacher tmp = teacherService.getUserById(userid);
            if (tmp == null) {
                res = Msg.Error("不存在用户名");
            } else if (password.equals(tmp.getPassword())) {
                res = Msg.Success("成功", null);
                username = tmp.getTname();
            } else {
                res = Msg.Error("错误密码");
            }
        } else {
            res = Msg.Error("错误的用户类型");
        }
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("user_name", username);
        res.put("data", data);


        if (res.get("code").equals(200)) {
            request.getSession().setAttribute("login", "1");
            request.getSession().setAttribute("userid", userid);
            request.getSession().setAttribute("role", role);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Map<String, Object> logout(HttpServletRequest request, Model model) {
        request.getSession().removeAttribute("login");
        request.getSession().removeAttribute("userid");
        request.getSession().removeAttribute("role");
        return Msg.Success("成功注销");
    }

    @ResponseBody
    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public Map<String, Object> changePassword(HttpServletRequest request, Model model) {
        String userid = request.getSession().getAttribute("userid").toString();
        String role = request.getSession().getAttribute("role").toString();
        String oldpsd = request.getParameter("old");
        String newpsd = request.getParameter("new");
        return userService.change_password(userid, role, MD5Util.crypt(oldpsd), MD5Util.crypt(newpsd));
    }

    @ResponseBody
    @RequestMapping(value = "/showStatue", method = RequestMethod.GET)
    public Map<String, Object> showStatue(HttpServletRequest request, Model model) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("userid", (String) request.getSession().getAttribute("userid"));
        res.put("login", (String) request.getSession().getAttribute("login"));
        res.put("role", (String) request.getSession().getAttribute("role"));
        return Msg.Success("成功", res);
    }
}
