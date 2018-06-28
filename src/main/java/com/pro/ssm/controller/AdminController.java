package com.pro.ssm.controller;

import com.pro.ssm.enumcase.CodeType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @ResponseBody
    @RequestMapping(value = "/base_info",method = RequestMethod.GET)
    public Map<String,Object> getBaseInfo(HttpServletRequest request, Model model){
        Map<String,Object> res = new HashMap<String, Object>();
        //!!!!!获取信息
        res.put("code",CodeType.UNFINISHED);
        res.put("msg","");
        Map<String,Object> tmp = new HashMap<String, Object>();
        tmp.put("stu_num",10);
        tmp.put("course_num",10);
        tmp.put("teacher_num",10);
        tmp.put("class_num",10);
        res.put("data",tmp);
        //
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/stu/list",method = RequestMethod.POST)
    public Map<String,Object> getStuList(HttpServletRequest request, Model model){
        Map<String,Object> res = new HashMap<String, Object>();
        int st = Integer.parseInt(request.getParameter("start"));
        int ed = Integer.parseInt(request.getParameter("n"));
        //!!!!!
        //
        return res;
    }
}
