package com.pro.ssm.controller;


import com.pro.ssm.dao.*;
import com.pro.ssm.model.*;
import com.pro.ssm.model.custom.*;
import com.pro.ssm.util.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.crypto.MacSpi;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Controller
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

    @ResponseBody
    @RequestMapping(value = "/teacher/search", method = RequestMethod.POST)
    public Map<String, Object> searchTeacher(HttpServletRequest request, Model model){
        return new AdminController().searchTeacher(request, model);
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/list", method = RequestMethod.POST)
    public Map<String, Object> listTeacher(HttpServletRequest request, Model model){
        return new AdminController().listTeacher(request, model);
    }

    /*课程搜索*/
    @ResponseBody
    @RequestMapping(value = "/course/search", method = RequestMethod.POST)
    public Map<String, Object> course_search(@RequestParam("key") String key, HttpSession session) {
        String tid = (String) session.getAttribute("userid");
        List<CourseInfo> res = iteacherDao.searchCourseList(tid,key);
        return Msg.Success(res);
    }

    /*课程信息*/
    @ResponseBody
    @RequestMapping(value = "/course/info", method = RequestMethod.GET)
    public Map<String, Object> course_info(@RequestParam("cid") int cid, HttpSession session) {
        List<CourseDetailInfo> res = iteacherDao.getCourseDetail(cid);
        return Msg.Success(res);
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
    @RequestMapping(value = "/commit_grade", method = RequestMethod.POST)
    public Map<String, Object> commit_grade(@RequestParam("id") int clsid, HttpSession session, HttpServletRequest request) {
        String tid = (String) session.getAttribute("userid");
        Cls cls = clsDao.selectByPrimaryKey(clsid);
        if (cls == null || !(cls.getTid().equals(tid)))
            return Msg.Error("教学班不存在或者您不是教学班的任课教师");

        Map<String, String[]> res = request.getParameterMap();
        StuClsKey k = new StuClsKey();
        k.setClsid(clsid);
        Set<String> used = new HashSet<String>();
        for (String key : res.keySet()) {
            String stuid;
            try {
                stuid = key.substring(6);
            } catch (IndexOutOfBoundsException e) {
                continue;
            }
            if (!used.contains(stuid)) {
                used.add(stuid);
                k.setSid(stuid);
                StuCls s = stuclsDao.selectByPrimaryKey(k);
                s.setUsualGrade(new BigDecimal(res.get("usual_" + stuid)[0]));
                s.setFinalGrade(new BigDecimal(res.get("final_" + stuid)[0]));
                s.setCreateTime(new Date());
                stuclsDao.updateByPrimaryKeySelective(s);
            }
        }
        return Msg.Success("录入成绩成功");
    }
}
