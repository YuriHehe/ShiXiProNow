package com.pro.ssm.ccontroller;

import com.pro.ssm.model.Admin;
import com.pro.ssm.service.AdminService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
}
