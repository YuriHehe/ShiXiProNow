package com.pro.ssm.controller;

import com.pro.ssm.enumcase.CodeType;
import com.pro.ssm.message.Msg;
import com.pro.ssm.model.*;
import com.pro.ssm.service.*;
import com.sun.xml.internal.ws.api.message.MessageWritable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public Map<String,Object> getDepartmentList(HttpServletRequest request, Model model) {
        List<Departmet> res = departmentService.selectAll();
        List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
        for (Departmet i : res) {
            Map<String,Object> tt = new HashMap<String, Object>();
            tt.put("did",i.getDid());
            tt.put("dname",i.getDname());
            tmp.add(tt);
        }
        return Msg.Success("成功获取信息", tmp);
    }

    @ResponseBody
    @RequestMapping(value = "/stu/list",method = RequestMethod.POST)
    public Map<String,Object> getStuList(HttpServletRequest request, Model model){
        int st = Integer.parseInt(request.getParameter("start"));
        int ed = Integer.parseInt(request.getParameter("n"));
        List<Student> res = studentService.selectSome(st,ed);
        List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
        for (Student i : res) {
            Map<String,Object> tt = new HashMap<String, Object>();
            tt.put("stuid",i.getSid());
            tt.put("name",i.getSname());
            String dname = departmentService.getDname(i.getDid());
            tt.put("department",dname);
            tt.put("grade",i.getGrade());
            tt.put("start_year",i.getStartYear());
            tt.put("sex",i.getSex());
            tt.put("class",i.getClass());
            tmp.add(tt);
        }
        return Msg.Success("成功获取信息", tmp);
    }

    @ResponseBody
    @RequestMapping(value = "/stu/search",method = RequestMethod.POST)
    public Map<String,Object> studentSearch(HttpServletRequest request, Model model){
        String key = request.getParameter("key");
        List<Student> res = studentService.selectBySearch(key);
        List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
        for (Student i : res) {
            Map<String,Object> tt = new HashMap<String, Object>();
            tt.put("stuid",i.getSid());
            tt.put("name",i.getSname());
            String dname = departmentService.getDname(i.getDid());
            tt.put("department",dname);
            tt.put("grade",i.getGrade());
            tt.put("start_year",i.getStartYear());
            tt.put("sex",i.getSex());
            tt.put("class",i.getClass());
            tmp.add(tt);
        }
        return Msg.Success("成功搜索",tmp);
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
            String dname = departmentService.getDname(stu.getDid());
            res.put("department",dname);
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
        List<MessageWithBLOBs> res = msgService.selectUnReply(10);
        List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
        for (MessageWithBLOBs i : res) {
            Map<String,Object> tt = new HashMap<String, Object>();
            tt.put("id",i.getMsgId());
            tt.put("stuid",i.getSid());
            String sname = studentService.getSname(i.getSid());
            tt.put("stu_name",sname);
            tt.put("content",i.getContent());
            tt.put("reply",i.getReply());
            tt.put("reply_time",i.getReplyTime());
            tt.put("create_time",i.getCreateTime());
            tmp.add(tt);
        }
        return Msg.Success("成功获取信息",tmp);
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

    @ResponseBody
    @RequestMapping(value = "/teacher/add",method = RequestMethod.POST)
    public Map<String,Object> addTeacher(HttpServletRequest request, Model model){
        Teacher res = new Teacher();
        res.setTid(request.getParameter("tid"));
        res.setPassword(request.getParameter("password"));
        res.setTname(request.getParameter("name"));
        res.setTitle(request.getParameter("title"));
        res.setDid(Integer.parseInt(request.getParameter("did")));
        res.setContact(request.getParameter("contact"));
        res.setAddress(request.getParameter("address"));
        boolean tag = teacherService.insertUser(res);
        if(tag == true){
            return Msg.Success("成功增加教师",null);
        }
        else{
            return Msg.Error("已存在该教师");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/teacher/edit",method = RequestMethod.POST)
    public Map<String,Object> editTeacher(HttpServletRequest request, Model model){
        Teacher res = new Teacher();
        res.setTid(request.getParameter("tid"));
        res.setPassword(request.getParameter("password"));
        res.setTname(request.getParameter("name"));
        res.setTitle(request.getParameter("title"));
        res.setDid(Integer.parseInt(request.getParameter("did")));
        res.setContact(request.getParameter("contact"));
        res.setAddress(request.getParameter("address"));
        boolean tag = teacherService.updateUser(res);
        if(tag == true){
            return Msg.Success("成功修改教师",null);
        }
        else{
            return Msg.Error("不存在该教师");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/teacher/del",method = RequestMethod.POST)
    public Map<String,Object> delTeacher(HttpServletRequest request, Model model){
        String userid = request.getParameter("tid");
        boolean tag = teacherService.delUserById(userid);
        if(tag == true){
            return Msg.Success("成功删除教师",null);
        }
        else{
            return Msg.Error("不存在该教师");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/teacher/search",method = RequestMethod.POST)
    public Map<String,Object> searchTeacher(HttpServletRequest request, Model model){
        String key = request.getParameter("key");
        List<Teacher> res = teacherService.selectBySearch(key);
        List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
        for (Teacher i : res) {
            Map<String,Object> tt = new HashMap<String, Object>();
            tt.put("tid",i.getTid());
            tt.put("name",i.getTname());
            tt.put("title",i.getTitle());
            String dname = departmentService.getDname(i.getDid());
            tt.put("department",dname);
            tt.put("contact",i.getContact());
            tt.put("address",i.getAddress());
            tmp.add(tt);
        }
        return Msg.Success("成功获取信息",tmp);
    }
    @ResponseBody
    @RequestMapping(value = "/teacher/list",method = RequestMethod.GET)
    public Map<String,Object> listTeacher(HttpServletRequest request, Model model){
        int st = Integer.parseInt(request.getParameter("start"));
        int n = Integer.parseInt(request.getParameter("n"));
        List<Teacher> res = teacherService.selectSome(st,n);
        List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
        for (Teacher i : res) {
            Map<String,Object> tt = new HashMap<String, Object>();
            tt.put("tid",i.getTid());
            tt.put("name",i.getTname());
            tt.put("title",i.getTitle());
            String dname = departmentService.getDname(i.getDid());
            tt.put("department",dname);
            tt.put("contact",i.getContact());
            tt.put("address",i.getAddress());
            tmp.add(tt);
        }
        return Msg.Success("成功获取信息",tmp);
    }
    @ResponseBody
    @RequestMapping(value = "/teacher/info",method = RequestMethod.GET)
    public Map<String,Object> infoTeacher(HttpServletRequest request, Model model){
        String tid = request.getParameter("tid");
        Teacher tea = teacherService.getUserById(tid);
        if(tea == null){
            return Msg.Error("不存在该老师");
        }
        else{
            Map<String,Object> res = new HashMap<String, Object>();
            res.put("tid",tea.getTid());
            res.put("name",tea.getTname());
            res.put("title",tea.getTitle());
            res.put("department",tea.getDid());
            res.put("contact",tea.getContact());
            res.put("address",tea.getAddress());
            return Msg.Success("成功获取老师信息",res);
        }
    }
}
