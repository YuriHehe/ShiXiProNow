package com.pro.ssm.controller;

import com.pro.ssm.util.Msg;
import com.pro.ssm.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class AppController {

    private Logger log = Logger.getLogger(AppController.class);

    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/loginError", method = RequestMethod.GET)
    public Map<String,Object> loginError(HttpServletResponse response, HttpServletRequest request){
        return Msg.Error("权限错误");
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String,Object> login(HttpServletRequest request, Model model){
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        //检查是否存在于数据库
        Map<String,Object> res = userService.userCheck(userid,password,role);
        if(res.get("code").equals(Msg.CodeType.SUCCESS)){
            request.getSession().setAttribute("login","1");
            request.getSession().setAttribute("userid",userid);
            request.getSession().setAttribute("role",role);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public Map<String,Object> logout(HttpServletRequest request, Model model) {
        request.getSession().removeAttribute("login");
        request.getSession().removeAttribute("userid");
        request.getSession().removeAttribute("role");
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("code", Msg.CodeType.UNLOGIN);
        res.put("msg", "未登录");
        res.put("data", null);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public Map<String,Object> changePassword(HttpServletRequest request, Model model){
        Map<String,Object> res;
        if(request.getSession().getAttribute("login").equals("1")){
            String userid = request.getSession().getAttribute("userid").toString();
            String role = request.getSession().getAttribute("role").toString();
            String oldpsd = request.getParameter("old");
            String newpsd = request.getParameter("new");
            res = userService.change_password(userid,role,oldpsd,newpsd);
        }
        else{
            res = new HashMap<String, Object>();
            res.put("code", Msg.CodeType.UNLOGIN);
            res.put("msg", "未登录");
            res.put("data", null);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/testChangePsd",method = RequestMethod.GET)
    public Map<String,Object> testChangePsd(HttpServletRequest request, Model model){
        Map<String,Object> res;
        if(request.getSession().getAttribute("login").equals("1")){
            String userid = request.getSession().getAttribute("userid").toString();
            String role = request.getSession().getAttribute("role").toString();
            String oldpsd = request.getParameter("old");
            String newpsd = request.getParameter("new");
            res = userService.change_password(userid,role,oldpsd,newpsd);
        }
        else{
            res = new HashMap<String, Object>();
            res.put("code", Msg.CodeType.UNLOGIN);
            res.put("msg", "未登录");
            res.put("data", null);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/testLogin",method = RequestMethod.GET)
    public Map<String,Object> testLogin(HttpServletRequest request, Model model){
        Map<String,Object> res = new HashMap<String, Object>();
        request.getSession().setAttribute("login","1");
        request.getSession().setAttribute("userid","admin");
        request.getSession().setAttribute("role","admin");
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/showStatue",method = RequestMethod.GET)
    public Map<String,Object> showStatue(HttpServletRequest request, Model model){
        Map<String,Object> res = new HashMap<String, Object>();
        if(request.getSession().getAttribute("login") == null){
            res.put("userid", "null");
            res.put("msg", "未登录");
        }
        else{
            res.put("userid", (String)request.getSession().getAttribute("userid"));
            res.put("login", (String)request.getSession().getAttribute("login"));
            res.put("role", (String)request.getSession().getAttribute("role"));
        }
        return res;
    }
}
