package com.pro.ssm.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pro.ssm.dao.*;
import com.pro.ssm.model.*;
import com.pro.ssm.model.custom.*;
import com.pro.ssm.service.DepartmentService;
import com.pro.ssm.service.TeacherService;
import com.pro.ssm.util.Msg;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.crypto.MacSpi;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@CrossOrigin(maxAge = 3600)
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    private StuClsMapper stuclsDao;
    @Resource
    private DepartmetMapper deptDao;
    @Resource
    private TeacherMapper teacherDao;
    @Resource
    private ClsMapper clsDao;
    @Resource
    private MyTeacherDao iteacherDao;

    /*教师账号信息*/
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Map<String, Object> info(HttpServletRequest request, HttpSession session) {
        final Teacher t = teacherDao.selectByPrimaryKey((String) session.getAttribute("userid"));

        return Msg.Success(new HashMap<String, Object>() {
            {
                put("tid", t.getTid());
                put("name", t.getTname());
                put("title", t.getTitle());
                put("department", deptDao.selectByPrimaryKey(t.getDid()).getDname());
                put("contact", t.getContact());
                put("address", t.getAddress());
            }
        });
    }

    /*搜索教师*/
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

    @Resource
    private TeacherService teacherService;
    @Resource
    private DepartmentService departmentService;

    /*教师列表*/
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

    /*教师列表*/
    @ResponseBody
    @RequestMapping(value = "/course/list", method = RequestMethod.POST)
    public Map<String, Object> courseList(HttpSession session, @RequestParam("start") int start, @RequestParam("n") int n) {
        String tid = (String) session.getAttribute("userid");
        return Msg.Success(iteacherDao.getCourseList(tid, start, n));
    }

    /*课程搜索*/
    @ResponseBody
    @RequestMapping(value = "/course/search", method = RequestMethod.POST)
    public Map<String, Object> course_search(@RequestParam("key") String key, HttpSession session) {
        String tid = (String) session.getAttribute("userid");
        List<CourseInfo> res = iteacherDao.searchCourseList(tid, key);
        return Msg.Success(res);
    }

    /*课程信息*/
    @ResponseBody
    @RequestMapping(value = "/course/info", method = RequestMethod.GET)
    public Map<String, Object> course_info(@RequestParam("cid") int cid, HttpSession session) {
        List<CourseDetailInfo> res = iteacherDao.getCourseDetail(cid);
        return Msg.Success(res);
    }

    /*教师课表信息*/
    @ResponseBody
    @RequestMapping(value = "/class_table", method = RequestMethod.GET)
    public Map<String, Object> class_table(@RequestParam("week") int week, HttpSession session) {
        if (week < 1)
            return Msg.Error("周数必须大于0!!");
        String tid = (String) session.getAttribute("userid");
        ArrayList<ClassTable>[] cls_tab = new ArrayList[]{
                new ArrayList<ClassTable>(),
                new ArrayList<ClassTable>(),
                new ArrayList<ClassTable>(),
                new ArrayList<ClassTable>(),
                new ArrayList<ClassTable>(),
                new ArrayList<ClassTable>(),
                new ArrayList<ClassTable>(),
        };

        List<ClassTable> cts = iteacherDao.getClasstabeByTid(tid, week);
        for (ClassTable c : cts) {
            cls_tab[c.getDay() - 1].add(c);
        }

        return Msg.Success(cls_tab);
    }

    /*教师任教教学班列表*/
    @ResponseBody
    @RequestMapping(value = "/class_list", method = RequestMethod.GET)
    public Map<String, Object> class_list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "n", defaultValue = "10") int n, HttpSession session) {
        String tid = (String) session.getAttribute("userid");
        List<ClsInfo> res = iteacherDao.getTeacherClass(tid, start, n);
        return Msg.Success(res);
    }

    /*教师任教教学班提交*/
    @ResponseBody
    @RequestMapping(value = "/class_add", method = RequestMethod.POST)
    public Map<String, Object> class_add(@RequestParam("id") int clsid, HttpSession session) {
        String tid = (String) session.getAttribute("userid");
        Cls c = clsDao.selectByPrimaryKey(clsid);
        if (c == null)
            return Msg.Error("所选择的教学班不存在!!");
        if (c.getTid() != null)
            return Msg.Error("所选择的教学班已经有老师任教!!");
        c.setTid(tid);
        clsDao.updateByPrimaryKeySelective(c);
        return Msg.Success();
    }

    /*教师任教教学班撤销*/
    @ResponseBody
    @RequestMapping(value = "/class_del", method = RequestMethod.POST)
    public Map<String, Object> class_del(@RequestParam("id") int clsid, HttpSession session) {
        String tid = (String) session.getAttribute("userid");
        Cls c = clsDao.selectByPrimaryKey(clsid);
        if (c == null)
            return Msg.Error("所选择的教学班不存在!!");
        if (!tid.equals(c.getTid()))
            return Msg.Error("您不是该教学班的任课教师!!");

        c.setTid(null);
        clsDao.updateByPrimaryKey(c);
        return Msg.Success();
    }

    /*教学班成绩列表*/
    @ResponseBody
    @RequestMapping(value = "/grade", method = RequestMethod.GET)
    public Map<String, Object> grade(@RequestParam("id") int clsid, HttpSession session) {
        String tid = (String) session.getAttribute("userid");
        Cls cls = clsDao.selectByPrimaryKey(clsid);
        if (cls == null || !(cls.getTid().equals(tid)))
            return Msg.Error("教学班不存在或者您不是教学班的任课教师");

        List<GradeBase> res = iteacherDao.getClassGradeList(clsid);
        return Msg.Success(res);
    }

    /*成绩录入*/
    @ResponseBody
    @Transactional
    @RequestMapping(value = "/commit_grade", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Map<String, Object> commit_grade(HttpSession session, HttpEntity<String> httpEntity) {
        String tid = (String) session.getAttribute("userid");
        JSONObject json = JSON.parseObject(httpEntity.getBody()); //反序列化
        Cls cls = clsDao.selectByPrimaryKey(json.getInteger("cls_id"));
        if (cls == null || !(cls.getTid().equals(tid)))
            return Msg.Error("教学班不存在或者您不是教学班的任课教师");


        StuClsKey k = new StuClsKey();
        k.setClsid(json.getInteger("cls_id"));
        JSONArray grades = json.getJSONArray("grades");
        for (int i = 0; i < grades.size(); i++) {
            JSONObject g = grades.getJSONObject(i);
            k.setSid(g.getString("stuid"));
            StuCls s = stuclsDao.selectByPrimaryKey(k);
            s.setUsualGrade(new BigDecimal(g.getString("usual_grade")));
            s.setFinalGrade(new BigDecimal(g.getString("final_grade")));
            s.setCreateTime(new Date());
            stuclsDao.updateByPrimaryKeySelective(s);
        }
        return Msg.Success("录入成绩成功");
    }
}
