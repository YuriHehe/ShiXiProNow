package com.pro.ssm.controller;

import com.pro.ssm.enumcase.CodeType;
import com.pro.ssm.message.Msg;
import com.pro.ssm.model.Course;
import com.pro.ssm.model.Student;
import com.pro.ssm.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    StudentService studentService;
    @Resource
    TeacherService teacherService;
    @Resource
    ClsService clsService;
    @Resource
    CourseService courseService;
    @Resource
    DepartmentService departmentService;
    @Resource
    MsgService msgService;


    @ResponseBody
    @RequestMapping(value = "/base_info",method = RequestMethod.GET)
    public Map<String,Object> getBaseInfo(HttpServletRequest request, Model model){
        Map<String,Object> tmp = new HashMap<String, Object>();
        tmp.put("stu_num",studentService.countStudentNum());
        tmp.put("course_num",courseService.countNum());
        tmp.put("teacher_num",teacherService.countNum());
        tmp.put("class_num",clsService.countNum());
        return Msg.Success("成功获取信息",tmp);
    }

    @ResponseBody
    @RequestMapping(value = "/department_list",method = RequestMethod.GET)
    public Map<String,Object> getDepartmentList(HttpServletRequest request, Model model){
        return Msg.Success("成功获取信息",departmentService.selectAll());
    }

    @ResponseBody
    @RequestMapping(value = "/stu/list",method = RequestMethod.POST)
    public Map<String,Object> getStuList(HttpServletRequest request, Model model){
        int st = Integer.parseInt(request.getParameter("start"));
        int ed = Integer.parseInt(request.getParameter("n"));
        return Msg.Success("成功获取信息",studentService.selectSome(st,ed));
    }

    @ResponseBody
    @RequestMapping(value = "/stu/search",method = RequestMethod.POST)
    public Map<String,Object> studentSearch(HttpServletRequest request, Model model){
        String key = request.getParameter("key");
        return Msg.Success("成功搜索",studentService.selectBySearch(key));
    }

    @ResponseBody
    @RequestMapping(value = "/stu/info",method = RequestMethod.GET)
    public Map<String,Object> getStudentInfo(HttpServletRequest request, Model model){
        String sid = request.getParameter("sid");
        Student stu = studentService.getUserById(sid);
        if(stu == null){
            return Msg.Error("不存在该学生");
        }
        else{
            Map<String,Object> res = new HashMap<String, Object>();
            res.put("stuid",stu.getSid());
            res.put("name",stu.getSname());
            res.put("department",stu.getDid());
            res.put("grade",stu.getGrade());
            res.put("start_year",stu.getStartYear());
            res.put("sex",stu.getSex());
            res.put("class",stu.getClass());
            return Msg.Success("成功获取学生信息",res);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/stu/add",method = RequestMethod.POST)
    public Map<String,Object> addStudent(HttpServletRequest request, Model model){
        Student stu = new Student();
        //读取
        stu.setSid(request.getParameter("stu_id"));
        stu.setSname(request.getParameter("name"));
        stu.setDid(Integer.parseInt(request.getParameter("did")));
        stu.setSex(request.getParameter("sex"));
        stu.setGrade(Integer.parseInt(request.getParameter("grade")));
        stu.setStartYear(Integer.parseInt(request.getParameter("start_year")));
        stu.setClsName(request.getParameter("class"));
        stu.setPassword(request.getParameter("password"));
        //添加
        boolean tag = studentService.insertUser(stu);
        if(tag == true){
            return Msg.Success("成功添加学生",null);
        }
        else{
            return Msg.Error("已存在该学生");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/stu/edit",method = RequestMethod.POST)
    public Map<String,Object> editStudent(HttpServletRequest request, Model model){
        String psd = request.getParameter("password");
        Student stu = new Student();
        //读取
        stu.setSid(request.getParameter("stu_id"));
        stu.setPassword(request.getParameter("password"));
        stu.setDid(Integer.parseInt(request.getParameter("did")));
        stu.setSex(request.getParameter("sex"));
        stu.setGrade(Integer.parseInt(request.getParameter("grade")));
        stu.setStartYear(Integer.parseInt(request.getParameter("start_year")));
        stu.setClsName(request.getParameter("class"));
        stu.setPassword(request.getParameter("password"));
        //更新
        boolean tag = studentService.updateUser(stu);
        if(tag == true){
            return Msg.Success("更新成功",null);
        }
        else{
            return Msg.Error("不存在该学生");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/stu/del_stu",method = RequestMethod.POST)
    public Map<String,Object> delStudent(HttpServletRequest request, Model model){
        Map<String,Object> res = new HashMap<String, Object>();
        String stuId = request.getParameter("stu_id");
        boolean tag = studentService.delUserById(stuId);
        if(tag == true){
            return Msg.Success("成功删除学生", null);
        }
        else{
            return Msg.Error("不存在学生");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/message/list",method = RequestMethod.GET)
    public Map<String,Object> showMessageList(){
        return Msg.Success("成功获取信息",msgService.selectUnReply(10));
    }

    @ResponseBody
    @RequestMapping(value = "/message/reply",method = RequestMethod.POST)
    public Map<String,Object> replyMsg(HttpServletRequest request, Model model){
        int msgid = Integer.parseInt(request.getParameter("message_id"));
        String content = request.getParameter("content");
        boolean tag = msgService.replyMessage(msgid,content);
        if(tag == true){
            return Msg.Success("成功回复留言",null);
        }
        else{
            return Msg.Error("不存在该留言");
        }
    }
}
