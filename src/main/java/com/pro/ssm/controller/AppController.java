package com.pro.ssm.controller;

import com.pro.ssm.model.Admin;
import com.pro.ssm.service.AdminService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class AppController {

    private Logger log = Logger.getLogger(AppController.class);

    @Resource(name = "adminService")
    private AdminService adminService;

    @RequestMapping("/showUser")
    public String showUser(HttpServletRequest request, Model model){
        log.info("查询所有用户信息");
        List<Admin> userList = adminService.getAllUser();
        model.addAttribute("userList",userList);
        return "showUser";
    }

    @RequestMapping("/set")
    public String setSession(@RequestParam("browser") String a, @RequestParam("browser") String browser, HttpSession session){
        log.info("查询所有用户信息");
        session.setAttribute("name", "");
        return "showUser";
    }

    @RequestMapping("/show")
    public String showSession(HttpServletRequest request, HttpSession session){
        log.info("查询所有用户信息");
        List<Admin> userList = adminService.getAllUser();
        return "showUser";
    }
}
