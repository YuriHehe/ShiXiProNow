package com.pro.ssm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pro.ssm.dao.*;
import com.pro.ssm.model.custom.CourseDetailInfo;
import com.pro.ssm.model.custom.CourseInfo;
import com.pro.ssm.model.custom.GradeBase;
import com.pro.ssm.model.custom.extra.Classes;
import com.pro.ssm.util.BeanUtil;
import com.pro.ssm.util.ExcelUtil;
import com.pro.ssm.util.MD5Util;
import com.pro.ssm.util.Msg;
import com.pro.ssm.model.*;
import com.pro.ssm.service.*;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@CrossOrigin(maxAge = 3600)
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private StudentService studentService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private ClsService clsService;
    @Resource
    private CourseService courseService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private MsgService msgService;
    @Resource
    private MyAdminDao iadminDao;
    @Resource
    private DepartmetMapper departmetDao;
    @Resource
    private MyTeacherDao iteacherDao;
    @Resource
    private ClsMapper clsDao;
    @Resource
    private ScheduleMapper scheduleDao;
    @Resource
    private SettingsMapper settingDao;
    @Resource
    private CourseMapper courseDao;

    /*通过学院名获取学院id*/
    private int getDeptId(String dept_name) {

        List<Departmet> dids = departmetDao.selectAll();
        int did = -1;
        for (Departmet d : dids) {
            if (d.getDname().equals(dept_name)) {
                return d.getDid();
            }
        }
        return -1;
    }

    @ResponseBody
    @RequestMapping(value = "/base_info", method = RequestMethod.GET)
    public Map<String, Object> getBaseInfo(HttpServletRequest request, Model model) {
        Map<String, Object> tmp = new HashMap<String, Object>();
        Settings term = settingDao.selectByPrimaryKey("term");
        Settings state = settingDao.selectByPrimaryKey("state");
        tmp.put("stu_num", studentService.countStudentNum());
        tmp.put("course_num", courseService.countNum());
        tmp.put("teacher_num", teacherService.countNum());
        tmp.put("class_num", clsService.countNum());
        tmp.put("term", term.getSvalue());
        tmp.put("state", Integer.parseInt(state.getSvalue()));
        tmp.put("dept_info", iadminDao.deptCourseInfo());
        return Msg.Success("成功获取信息", tmp);
    }

    @ResponseBody
    @RequestMapping(value = "/department_list", method = RequestMethod.GET)
    public Map<String, Object> getDepartmentList(HttpServletRequest request, Model model) {
        List<Departmet> res = departmentService.selectAll();
        List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
        for (Departmet i : res) {
            Map<String, Object> tt = new HashMap<String, Object>();
            tt.put("did", i.getDid());
            tt.put("dname", i.getDname());
            tmp.add(tt);
        }
        return Msg.Success("成功获取信息", tmp);
    }

    @ResponseBody
    @RequestMapping(value = "/stu/list", method = RequestMethod.POST)
    public Map<String, Object> getStuList(HttpServletRequest request, Model model) {
        int st = Integer.parseInt(request.getParameter("start"));
        int ed = Integer.parseInt(request.getParameter("n"));
        List<Student> res = studentService.selectSome(st, ed);
        List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
        for (Student i : res) {
            Map<String, Object> tt = new HashMap<String, Object>();
            tt.put("stuid", i.getSid());
            tt.put("name", i.getSname());
            String dname = departmentService.getDname(i.getDid());
            tt.put("department", dname);
            tt.put("grade", i.getGrade());
            tt.put("start_year", i.getStartYear());
            tt.put("sex", i.getSex());
            tt.put("class_name", i.getClass());
            tmp.add(tt);
        }
        return Msg.Success("成功获取信息", tmp);
    }

    @ResponseBody
    @RequestMapping(value = "/stu/search", method = RequestMethod.POST)
    public Map<String, Object> studentSearch(HttpServletRequest request, Model model) {
        String key = request.getParameter("key");
        List<Student> res = studentService.selectBySearch(key);
        List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
        for (Student i : res) {
            Map<String, Object> tt = new HashMap<String, Object>();
            tt.put("stuid", i.getSid());
            tt.put("name", i.getSname());
            String dname = departmentService.getDname(i.getDid());
            tt.put("department", dname);
            tt.put("grade", i.getGrade());
            tt.put("start_year", i.getStartYear());
            tt.put("sex", i.getSex());
            tt.put("class_name", i.getClass());
            tmp.add(tt);
        }
        return Msg.Success("成功搜索", tmp);
    }

    @ResponseBody
    @RequestMapping(value = "/stu/info", method = RequestMethod.GET)
    public Map<String, Object> getStudentInfo(HttpServletRequest request, Model model) {
        String sid = request.getParameter("sid");
        Student stu = studentService.getUserById(sid);
        if (stu == null) {
            return Msg.Error("不存在该学生");
        } else {
            Map<String, Object> res = new HashMap<String, Object>();
            res.put("stuid", stu.getSid());
            res.put("name", stu.getSname());
            String dname = departmentService.getDname(stu.getDid());
            res.put("department", dname);
            res.put("grade", stu.getGrade());
            res.put("start_year", stu.getStartYear());
            res.put("sex", stu.getSex());
            res.put("class_name", stu.getClsName());
            return Msg.Success("成功获取学生信息", res);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/stu/add", method = RequestMethod.POST)
    public Map<String, Object> addStudent(
            @RequestParam("stu_id") String stu_id,
            @RequestParam("name") String name,
            @RequestParam("department") String dname,
            @RequestParam("sex") String sex,
            @RequestParam("grade") int grade,
            @RequestParam("start_year") int start_year,
            @RequestParam("class") String class_name,
            @RequestParam("password") String pwd
    ) {
        int did = getDeptId(dname);
        if (did == -1)
            return Msg.Error("学院不存在");

        Student stu = new Student();
        stu.setSid(stu_id);
        stu.setSname(name);
        stu.setDid(did);
        stu.setSex(sex);
        stu.setGrade(grade);
        stu.setStartYear(start_year);
        stu.setClsName(class_name);
        stu.setPassword(MD5Util.crypt(pwd));
        //添加
        boolean tag = studentService.insertUser(stu);
        if (tag == true) {
            return Msg.Success("成功添加学生", null);
        } else {
            return Msg.Error("已存在该学生");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/stu/edit", method = RequestMethod.POST)
    public Map<String, Object> editStudent(
            @RequestParam("stu_id") String stu_id,
            @RequestParam("name") String name,
            @RequestParam("department") String dname,
            @RequestParam("sex") String sex,
            @RequestParam("grade") int grade,
            @RequestParam("start_year") int start_year,
            @RequestParam("class") String class_name,
            @RequestParam(value = "password", defaultValue = "") String pwd
    ) {
        int did = getDeptId(dname);
        if (did == -1)
            return Msg.Error("学院不存在");

        Student stu = new Student();
        stu.setSid(stu_id);
        stu.setSname(name);
        stu.setDid(did);
        stu.setSex(sex);
        stu.setGrade(grade);
        stu.setStartYear(start_year);
        stu.setClsName(class_name);
        if (pwd != null && pwd.length() > 0) {
            stu.setPassword(MD5Util.crypt(pwd));
        }

        //更新
        boolean tag = studentService.updateUser(stu);
        if (tag == true) {
            return Msg.Success("更新成功", null);
        } else {
            return Msg.Error("不存在该学生");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/del_stu", method = RequestMethod.POST)
    public Map<String, Object> delStudent(HttpServletRequest request, Model model) {
        Map<String, Object> res = new HashMap<String, Object>();
        String stuId = request.getParameter("stu_id");
        boolean tag = studentService.delUserById(stuId);
        if (tag == true) {
            return Msg.Success("成功删除学生", null);
        } else {
            return Msg.Error("不存在学生");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/message/list", method = RequestMethod.GET)
    public Map<String, Object> showMessageList() {
        return Msg.Success("成功获取信息", iadminDao.getMessage());
    }

    @ResponseBody
    @RequestMapping(value = "/message/reply", method = RequestMethod.POST)
    public Map<String, Object> replyMsg(HttpServletRequest request, Model model) {
        int msgid = Integer.parseInt(request.getParameter("message_id"));
        String content = request.getParameter("content");
        boolean tag = msgService.replyMessage(msgid, content);
        if (tag == true) {
            return Msg.Success("成功回复留言", null);
        } else {
            return Msg.Error("不存在该留言");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/add", method = RequestMethod.POST)
    public Map<String, Object> addTeacher(HttpServletRequest request, Model model) {
        int did = getDeptId(request.getParameter("did"));
        if (did == -1)
            return Msg.Error("学院不存在");

        Teacher res = new Teacher();
        res.setTid(request.getParameter("tid"));
        res.setPassword(MD5Util.crypt(request.getParameter("password")));
        res.setTname(request.getParameter("name"));
        res.setTitle(request.getParameter("title"));
        res.setDid(did);
        res.setContact(request.getParameter("contact"));
        res.setAddress(request.getParameter("address"));
        boolean tag = teacherService.insertUser(res);
        if (tag == true) {
            return Msg.Success("成功增加教师", null);
        } else {
            return Msg.Error("已存在该教师");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/edit", method = RequestMethod.POST)
    public Map<String, Object> editTeacher(HttpServletRequest request, Model model) {
        int did = getDeptId(request.getParameter("did"));
        if (did == -1)
            return Msg.Error("学院不存在");

        Teacher res = new Teacher();
        res.setTid(request.getParameter("tid"));
        res.setPassword(request.getParameter("password"));
        res.setTname(request.getParameter("name"));
        res.setTitle(request.getParameter("title"));
        res.setDid(did);
        res.setContact(request.getParameter("contact"));
        res.setAddress(request.getParameter("address"));
        boolean tag = teacherService.updateUser(res);
        if (tag == true) {
            return Msg.Success("成功修改教师", null);
        } else {
            return Msg.Error("不存在该教师");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/del", method = RequestMethod.POST)
    public Map<String, Object> delTeacher(HttpServletRequest request, Model model) {
        String userid = request.getParameter("tid");
        boolean tag = teacherService.delUserById(userid);
        if (tag == true) {
            return Msg.Success("成功删除教师", null);
        } else {
            return Msg.Error("不存在该教师");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/search", method = RequestMethod.POST)
    public Map<String, Object> searchTeacher(HttpServletRequest request, Model model) {
        String key = request.getParameter("key");
        List<Teacher> res = teacherService.selectBySearch(key);
        List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
        for (Teacher i : res) {
            Map<String, Object> tt = new HashMap<String, Object>();
            tt.put("tid", i.getTid());
            tt.put("name", i.getTname());
            tt.put("title", i.getTitle());
            String dname = departmentService.getDname(i.getDid());
            tt.put("department", dname);
            tt.put("contact", i.getContact());
            tt.put("address", i.getAddress());
            tmp.add(tt);
        }
        return Msg.Success("成功获取信息", tmp);
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/list", method = RequestMethod.GET)
    public Map<String, Object> listTeacher(HttpServletRequest request, Model model) {
        int st = Integer.parseInt(request.getParameter("start"));
        int n = Integer.parseInt(request.getParameter("n"));
        List<Teacher> res = teacherService.selectSome(st, n);
        List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
        for (Teacher i : res) {
            Map<String, Object> tt = new HashMap<String, Object>();
            tt.put("tid", i.getTid());
            tt.put("name", i.getTname());
            tt.put("title", i.getTitle());
            String dname = departmentService.getDname(i.getDid());
            tt.put("department", dname);
            tt.put("contact", i.getContact());
            tt.put("address", i.getAddress());
            tmp.add(tt);
        }
        return Msg.Success("成功获取信息", tmp);
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/info", method = RequestMethod.GET)
    public Map<String, Object> infoTeacher(HttpServletRequest request, Model model) {
        String tid = request.getParameter("tid");
        Teacher tea = teacherService.getUserById(tid);
        if (tea == null) {
            return Msg.Error("不存在该老师");
        } else {
            Map<String, Object> res = new HashMap<String, Object>();
            res.put("tid", tea.getTid());
            res.put("name", tea.getTname());
            res.put("title", tea.getTitle());
            String dname = departmentService.getDname(tea.getDid());
            res.put("department", dname);
            res.put("contact", tea.getContact());
            res.put("address", tea.getAddress());
            return Msg.Success("成功获取老师信息", res);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/course/add_old", method = RequestMethod.POST)
    public Map<String, Object> addCourseOld(HttpServletRequest request, Model model) {
        Course tmp = new Course();
        tmp.setCname(request.getParameter("name"));
        tmp.setDid(Integer.parseInt(request.getParameter("did")));
        BigDecimal bb = new BigDecimal(request.getParameter("credit"));
        tmp.setCredit(bb);
        tmp.setHour(Integer.parseInt(request.getParameter("hour")));
        boolean tag = courseService.insertCs(tmp);
        if (tag == true) {
            return Msg.Success("成功增加课程", null);
        } else {
            return Msg.Error("课程已经存在");
        }
    }

    @ResponseBody
    @Transactional
    @RequestMapping(value = "/course/add", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Map<String, Object> addCourse(HttpEntity<String> httpEntity) {
        System.out.println(httpEntity.getBody());
        JSONObject json = JSON.parseObject(httpEntity.getBody()); //反序列化

        int did = getDeptId(json.getString("department"));
        if(did==-1)
            return Msg.Error("学院不存在！");

        Course course = new Course();
        course.setCname(json.getString("name"));
        course.setDid(did);
        course.setCredit(new BigDecimal(json.getString("credit")));
        course.setHour(json.getInteger("hour"));
        courseDao.insertSelective(course);

        for(Object cls_obj:json.getJSONArray("class")){
            JSONObject cls_json = (JSONObject) cls_obj;
            Cls cls = new Cls();
            cls.setCid(course.getCid());
            cls.setChoseNum(0);
            cls.setCapacity(cls_json.getInteger("capacity"));
            cls.setTerm(cls_json.getString("term"));
            clsDao.insertSelective(cls);

            for(Object schdule_obj:cls_json.getJSONArray("schedule")){
                JSONObject schedule_json = (JSONObject) schdule_obj;

                String week_range = schedule_json.getString("week_range");
                String time_range = schedule_json.getString("time_range");
                int week_delim_pos = week_range.indexOf('-');
                int time_delim_pos = time_range.indexOf('-');
                if (week_delim_pos == -1 || time_delim_pos == -1)
                    return Msg.Error("week_range或time_range格式不对");

                int start_week = Integer.parseInt(week_range.substring(0, week_delim_pos)),
                        end_week = Integer.parseInt(week_range.substring(week_delim_pos + 1)),
                        start_session = Integer.parseInt(time_range.substring(0, time_delim_pos)),
                        end_session = Integer.parseInt(time_range.substring(time_delim_pos + 1));

                if (start_week < 1 || end_week < start_week || start_session < 1 || end_session < start_session)
                    return Msg.Error("week_range或time_range内容不合法");

                Schedule s = new Schedule();
//                System.out.println(cls.getClsid());
                s.setClsid(cls.getClsid());
                s.setAddress(schedule_json.getString("address"));
                s.setStartWeek(start_week);
                s.setEndWeek(end_week);
                s.setStartSession(start_session);
                s.setEndSession(end_session);
                s.setDay(schedule_json.getInteger("day"));
                scheduleDao.insertSelective(s);
            }
        }
        return Msg.Success();
    }

    @ResponseBody
    @RequestMapping(value = "/course/eidt", method = RequestMethod.POST)
    public Map<String, Object> editCourse(HttpServletRequest request, Model model) {
        Course tmp = new Course();
        tmp.setCid(Integer.parseInt(request.getParameter("cid")));
        tmp.setCname(request.getParameter("name"));
        tmp.setDid(Integer.parseInt(request.getParameter("did")));
        BigDecimal bb = new BigDecimal(request.getParameter("credit"));
        tmp.setCredit(bb);
        tmp.setHour(Integer.parseInt(request.getParameter("hour")));
        boolean tag = courseService.updateCs(tmp);
        if (tag == true) {
            return Msg.Success("成功修改课程", null);
        } else {
            return Msg.Error("课程不存在");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/course/del", method = RequestMethod.POST)
    public Map<String, Object> delCourse(@RequestParam("key") int cid) {
        int affected_row = courseDao.deleteByPrimaryKey(cid);
        if (affected_row == 1) {
            return Msg.Success("成功删除课程");
        } else {
            return Msg.Error("课程不存在");
        }
    }

    /*课程搜索 by wang*/
    @ResponseBody
    @RequestMapping(value = "/course/search", method = RequestMethod.POST)
    public Map<String, Object> searchCourse(@RequestParam("key") String key) {
        return Msg.Success(iadminDao.searchCourseList(key));
    }

    /*课程列表 */
    @ResponseBody
    @RequestMapping(value = "/course/list", method = RequestMethod.GET)
    public Map<String, Object> listCourse(
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "n", defaultValue = "10") int n
    ) {
        return Msg.Success(iadminDao.getCourseList(start, n));
    }

    /*课程信息*/
    @ResponseBody
    @RequestMapping(value = "/course/info", method = RequestMethod.GET)
    public Map<String, Object> infoCourse(@RequestParam("cid") int cid) {
        return Msg.Success(iteacherDao.getCourseDetail(cid));
    }

    /*增加教学班*/
    @ResponseBody
    @RequestMapping(value = "/class/add", method = RequestMethod.POST)  // TODO POST
    public Map<String, Object> addClass(
            @RequestParam("cid") int cid, @RequestParam("term") String term,
            @RequestParam("capacity") int capacity
    ) {
        Cls cls = new Cls();
        cls.setCid(cid);
        cls.setTerm(term);
        cls.setCapacity(capacity);
        int affected_row = clsDao.insertSelective(cls);
        if (affected_row != 1)
            return Msg.Error("增加教学班失败");

        HashMap<String, Integer> res = new HashMap<String, Integer>();
        res.put("clsid", cls.getClsid());
        return Msg.Success(res);
    }

    /*修改教学班*/
    @ResponseBody
    @RequestMapping(value = "/class/edit", method = RequestMethod.POST)  // TODO POST
    public Map<String, Object> editClass(
            @RequestParam("id") int clsid, @RequestParam("cid") int cid,
            @RequestParam("term") String term, @RequestParam("capacity") int capacity
    ) {
        Cls cls = clsDao.selectByPrimaryKey(clsid);
        if (cls == null)
            return Msg.Error("教学班不存在");
        cls.setCid(cid);
        cls.setTerm(term);
        cls.setCapacity(capacity);
        clsDao.updateByPrimaryKeySelective(cls);
        return Msg.Success();
    }

    /*删除教学班*/
    @ResponseBody
    @RequestMapping(value = "/class/del", method = RequestMethod.POST)  // TODO POST
    public Map<String, Object> delClass(@RequestParam("id") int clsid) {
        int affect_row = clsDao.deleteByPrimaryKey(clsid);
        if (affect_row != 1)
            return Msg.Error("删除教学班失败:教学班不存在");
        return Msg.Success();
    }

    /*增加课表*/
    @ResponseBody
    @RequestMapping(value = "/schedule/add", method = RequestMethod.POST)  // TODO POST
    public Map<String, Object> addSchedule(
            @RequestParam("class_id") int clsid,
            @RequestParam("week_range") String week_range,
            @RequestParam("day") int day,
            @RequestParam("time_range") String time_range,
            @RequestParam("address") String address
    ) {
        int week_delim_pos = week_range.indexOf('-');
        int time_delim_pos = time_range.indexOf('-');
        if (week_delim_pos == -1 || time_delim_pos == -1)
            return Msg.Error("week_range或time_range格式不对");

        int start_week = Integer.parseInt(week_range.substring(0, week_delim_pos)),
                end_week = Integer.parseInt(week_range.substring(week_delim_pos + 1)),
                start_session = Integer.parseInt(time_range.substring(0, time_delim_pos)),
                end_session = Integer.parseInt(time_range.substring(time_delim_pos + 1));

        if (start_week < 1 || end_week < start_week || start_session < 1 || end_session < start_session)
            return Msg.Error("week_range或time_range内容不合法");

        Schedule s = new Schedule();
        s.setAddress(address);
        s.setDay(day);
        s.setClsid(clsid);
        s.setStartWeek(start_week);
        s.setEndWeek(end_week);
        s.setStartSession(start_session);
        s.setEndSession(end_session);
        scheduleDao.insertSelective(s);

        return Msg.Success();
    }

    /*修改课表*/
    @ResponseBody
    @RequestMapping(value = "/schedule/edit", method = RequestMethod.POST)  // TODO POST
    public Map<String, Object> editSchedule(
            @RequestParam("class_id") int clsid,
            @RequestParam("week_range") String week_range,
            @RequestParam("day") int day,
            @RequestParam("time_range") String time_range,
            @RequestParam("address") String address
    ) {
        int week_delim_pos = week_range.indexOf('-');
        int time_delim_pos = time_range.indexOf('-');
        if (week_delim_pos == -1 || time_delim_pos == -1)
            return Msg.Error("week_range或time_range格式不对");

        int start_week = Integer.parseInt(week_range.substring(0, week_delim_pos)),
                end_week = Integer.parseInt(week_range.substring(week_delim_pos + 1)),
                start_session = Integer.parseInt(time_range.substring(0, time_delim_pos)),
                end_session = Integer.parseInt(time_range.substring(time_delim_pos + 1));

        if (start_week < 1 || end_week < start_week || start_session < 1 || end_session < start_session)
            return Msg.Error("week_range或time_range内容不合法");

        ScheduleKey key = new ScheduleKey();
        key.setDay(day);
        key.setClsid(clsid);
        key.setStartWeek(start_week);
        key.setEndWeek(end_week);
        key.setStartSession(start_session);
        key.setEndSession(end_session);
        Schedule s = scheduleDao.selectByPrimaryKey(key);
        s.setAddress(address);
        scheduleDao.updateByPrimaryKey(s);
        return Msg.Success();
    }

    /*删除课表*/
    @ResponseBody
    @RequestMapping(value = "/schedule/del", method = RequestMethod.POST)  // TODO POST
    public Map<String, Object> delSchedule(
            @RequestParam("class_id") int clsid,
            @RequestParam("week_range") String week_range,
            @RequestParam("day") int day,
            @RequestParam("time_range") String time_range
    ) {
        int week_delim_pos = week_range.indexOf('-');
        int time_delim_pos = time_range.indexOf('-');
        if (week_delim_pos == -1 || time_delim_pos == -1)
            return Msg.Error("week_range或time_range格式不对");

        int start_week = Integer.parseInt(week_range.substring(0, week_delim_pos)),
                end_week = Integer.parseInt(week_range.substring(week_delim_pos + 1)),
                start_session = Integer.parseInt(time_range.substring(0, time_delim_pos)),
                end_session = Integer.parseInt(time_range.substring(time_delim_pos + 1));

        if (start_week < 1 || end_week < start_week || start_session < 1 || end_session < start_session)
            return Msg.Error("week_range或time_range内容不合法");

        ScheduleKey key = new ScheduleKey();
        key.setDay(day);
        key.setClsid(clsid);
        key.setStartWeek(start_week);
        key.setEndWeek(end_week);
        key.setStartSession(start_session);
        key.setEndSession(end_session);
        scheduleDao.deleteByPrimaryKey(key);
        return Msg.Success();
    }

    /* 查询学院成绩汇总信息*/
    @ResponseBody
    @RequestMapping(value = "/grade/department", method = RequestMethod.POST)  // TODO POST
    public Map<String, Object> departmentGrade(@RequestParam("did") int did) {
        return Msg.Success(iadminDao.getDeptCourseGrade(did));
    }

    /* 查询教学班成绩汇总信息*/
    @ResponseBody
    @RequestMapping(value = "/grade/class", method = RequestMethod.POST)  // TODO POST
    public Map<String, Object> classGrade(@RequestParam("class_id") int clsid) {
        return Msg.Success(iteacherDao.getClassGradeList(clsid));
    }

    static Map<String, String> department_grade_map = new ListOrderedMap<String, String>() {
        {
            put("term", "学期");
            put("cid", "课程id");
            put("name", "课程名称");
            put("credit", "学分");
            put("hour", "学分");
            put("class_id", "教学班id");
            put("teacher", "教师姓名");
            put("chose_num", "班级人数");
            put("avg_usual_grade", "平均平时成绩");
            put("avg_final_grade", "平均平时成绩");
            put("avg_grade", "平均成绩");
        }
    };

    /* 导出学院成绩汇总信息 */
    @ResponseBody
    @RequestMapping(value = "/grade/department/output", method = RequestMethod.GET)
    public Map<String, Object> departmentGradeOutput(
            @RequestParam("did") int did,
            HttpServletResponse response
    ) {
        List<CourseDetailInfo> res = iadminDao.getDeptCourseGrade(did);
        ArrayList<Map<String, Object>> grade_maps = new ArrayList<Map<String, Object>>();
        for (CourseDetailInfo c : res) {
            Map<String, Object> cmap = BeanUtil.transBean2Map(c);
            cmap.remove("classes");
            for (Classes cls : c.getClasses()) {
                Map<String, Object> clsmap = BeanUtil.transBean2Map(cls);
                clsmap.putAll(cmap);
                grade_maps.add(clsmap);
            }
        }

        try {
            response.setHeader("content-disposition",
                    "attachment;filename=" + URLEncoder.encode(did + "学院成绩汇总信息.xls", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            ExcelUtil.writeXls(grade_maps, did + "学院成绩汇总信息", department_grade_map, out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Msg.Success(grade_maps);
    }

    static Map<String, String> class_grade_map = new ListOrderedMap<String, String>() {
        {
            put("stuid", "学生ID");
            put("name", "学生姓名");
            put("usual_grade", "平时成绩");
            put("final_grade", "期末成绩");
            put("grade", "最终成绩");
        }
    };

    /* 导出教学班成绩汇总信息 */
    @ResponseBody
    @RequestMapping(value = "/grade/class/output", method = RequestMethod.GET)
    public Map<String, Object> classGradeOutput(
            @RequestParam("cls_id") int clsid,
            HttpServletResponse response
    ) {
        List<GradeBase> res = iteacherDao.getClassGradeList(clsid);
        ArrayList<Map<String, Object>> grade_maps = new ArrayList<Map<String, Object>>();
        for (GradeBase g : res) {
            grade_maps.add(BeanUtil.transBean2Map(g));
        }

        try {
            response.setHeader("content-disposition",
                    "attachment;filename=" + URLEncoder.encode("教学班" + clsid + "成绩汇总信息.xls", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            ExcelUtil.writeXls(grade_maps, "教学班" + clsid + "成绩汇总信息", class_grade_map, out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Msg.Success();
    }


    /* 修改学期 */
    @ResponseBody
    @RequestMapping(value = "/set_term", method = RequestMethod.POST)
    public Map<String, Object> setTerm(@RequestParam("term") String term) {
        Settings s = new Settings();
        s.setSkey("term");
        s.setSvalue(term);
        settingDao.updateByPrimaryKey(s);
        return Msg.Success();
    }

    /* 修改当前系统状态 */
    @ResponseBody
    @RequestMapping(value = "/set_state", method = RequestMethod.POST) // todo post
    public Map<String, Object> setState(@RequestParam("state") String state) {
        Settings s = new Settings();
        s.setSkey("state");
        s.setSvalue(state);
        settingDao.updateByPrimaryKey(s);
        return Msg.Success();
    }
}
