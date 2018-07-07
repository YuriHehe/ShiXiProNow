package com.pro.ssm.controller;

import com.pro.ssm.dao.*;
import com.pro.ssm.model.custom.extra.Classes;
import com.pro.ssm.util.Msg;
import com.pro.ssm.model.*;
import com.pro.ssm.model.custom.*;
import com.pro.ssm.util.GradeTool;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@CrossOrigin(maxAge = 3600)
@RequestMapping("/stu")
public class StuController {
    @Resource
    private StudentMapper stuDao;
    @Resource
    private StuClsMapper stuclsDao;
    @Resource
    private DepartmetMapper deptDao;
    @Resource
    private CourseMapper courseDao;
    @Resource
    private TeacherMapper teacherDao;
    @Resource
    private ScheduleMapper scheduleDao;
    @Resource
    private ClsMapper clsDao;
    @Resource
    private MyStudentDao istuDao;
    @Resource
    private MyTeacherDao iteacherDao;
    @Resource
    private MessageMapper msgDao;

    /*学生账号信息*/
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Map<String, Object> info(HttpServletRequest request, HttpSession session) {
        final Student stu = stuDao.selectByPrimaryKey((String) session.getAttribute("userid"));

        return Msg.Success(new HashMap<String, Object>() {
            {
                put("stuid", stu.getSid());
                put("name", stu.getSname());
                put("department", deptDao.selectByPrimaryKey(stu.getDid()).getDname());
                put("grade", stu.getGrade());
                put("start_year", stu.getStartYear());
                put("sex", stu.getSex());
                put("class", stu.getClsName());
            }
        });
    }

    /*成绩信息*/
    @ResponseBody
    @RequestMapping(value = "/grade", method = RequestMethod.GET)
    public Map<String, Object> grade(HttpServletRequest request, HttpSession session) {
        String stuid = (String) session.getAttribute("userid");
        List<GradeInfo> res = istuDao.getStuGrade(stuid);
        return Msg.Success(res);
    }

    /*课表信息*/
    @ResponseBody
    @RequestMapping(value = "/class_table", method = RequestMethod.GET)
    public Map<String, Object> class_table(@RequestParam("week") int week, HttpSession session) {
        if (week < 1)
            return Msg.Error("周数必须大于0!!");
        String stuid = (String) session.getAttribute("userid");
        ArrayList<ClassTable>[] cls_tab = new ArrayList[]{
                new ArrayList<ClassTable>(),
                new ArrayList<ClassTable>(),
                new ArrayList<ClassTable>(),
                new ArrayList<ClassTable>(),
                new ArrayList<ClassTable>(),
                new ArrayList<ClassTable>(),
                new ArrayList<ClassTable>(),
        };

        List<ClassTable> cts = istuDao.getClasstabeByStuid(stuid, week);
        for (ClassTable c : cts) {
            cls_tab[c.getDay() - 1].add(c);
        }

        return Msg.Success(cls_tab);
    }

    /*已选教学班信息*/
    @ResponseBody
    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public Map<String, Object> course(HttpServletRequest request, HttpSession session) {
        String stuid = (String) session.getAttribute("userid");
        List<CourseInfoWithCls> res = istuDao.getStuCourseByStuid(stuid);
        return Msg.Success(res);
    }

    /*可选课程和教学班信息*/
    @ResponseBody
    @RequestMapping(value = "/course_list", method = RequestMethod.GET)
    public Map<String, Object> course_list(HttpServletRequest request, HttpSession session) {
        String stuid = (String) session.getAttribute("userid");
        List<CourseInfo> res = istuDao.getStuAvailableCourseByStuid(stuid);
        return Msg.Success(res);
    }

    /*课程信息*/
    @ResponseBody
    @RequestMapping(value = "/course_info", method = RequestMethod.GET)
    public Map<String, Object> infoCourse(@RequestParam("cid") int cid) {
        return Msg.Success(iteacherDao.getCourseDetail(cid));
    }

    /*教学班信息*/
    @ResponseBody
    @RequestMapping(value = "/cls_info", method = RequestMethod.GET)
    public Map<String, Object> cls_info(@RequestParam("class_id") int clsid) {
        List<Classes> res = istuDao.getClsDetail(clsid);
        return Msg.Success(res);
    }

    /*查询教师开设的教学班*/
    @ResponseBody
    @RequestMapping(value = "/class_search", method = RequestMethod.POST)
    public Map<String, Object> class_search(@RequestParam("tname") String tname) {
        if (tname.length() == 0)
            return Msg.Error("请输入教师名!!");

        List<ClsInfo> res = istuDao.getClsByTeacheName(tname);
        return Msg.Success(res);
    }

    /*提交教学班*/
    @ResponseBody
    @Transactional
    @RequestMapping(value = "/commit_class", method = RequestMethod.POST)
    public Map<String, Object> commit_class(@RequestParam("class_id") int class_id, HttpSession session) {
        String stuid = (String) session.getAttribute("userid");
        Cls cls = clsDao.selectByPrimaryKey(class_id);
        if (cls.getChoseNum() >= cls.getCapacity()) {
            return Msg.Error("选课人数已满");
        }
        if (istuDao.isStuSelThisCourse(stuid, cls.getCid()) != 0) {
            return Msg.Error("您已经选过该课程！");
        }

        cls.setChoseNum(cls.getChoseNum() + 1);
        clsDao.updateByPrimaryKeySelective(cls);

        StuCls stucls = new StuCls();
        stucls.setSid(stuid);
        stucls.setClsid(class_id);
        try {
            int affected_row = stuclsDao.insertSelective(stucls);
            if (affected_row != 1)
                return Msg.Error("选课失败!!");
        } catch (DuplicateKeyException e) {
            return Msg.Error("你已经选择过该教学班,不可重复选择!");
        }

        return Msg.Success();
    }

    /*退选教学班*/
    @ResponseBody
    @Transactional
    @RequestMapping(value = "/del_class", method = RequestMethod.POST)
    public Map<String, Object> del_class(@RequestParam("class_id") int class_id, HttpSession session) {
        String stuid = (String) session.getAttribute("userid");
        // 教学班人数减一
        Cls cls = clsDao.selectByPrimaryKey(class_id);
        cls.setChoseNum(cls.getChoseNum() - 1);
        clsDao.updateByPrimaryKeySelective(cls);

        StuClsKey key = new StuClsKey();
        key.setSid(stuid);
        key.setClsid(class_id);
        int affected_row = stuclsDao.deleteByPrimaryKey(key);
        if (affected_row != 1)
            return Msg.Error("退课失败!!");
        return Msg.Success();
    }

    /*留言*/
    @ResponseBody
    @RequestMapping(value = "/report_message", method = RequestMethod.POST)
    public Map<String, Object> report_message(@RequestParam("content") String content, HttpSession session) {
        String stuid = (String) session.getAttribute("userid");
        MessageWithBLOBs msg = new MessageWithBLOBs();
        msg.setSid(stuid);
        msg.setContent(content);
        msg.setCreateTime(new Date());
        int affected_row = msgDao.insertSelective(msg);
        if (affected_row != 1)
            return Msg.Error("留言失败!!");
        return Msg.Success();

    }

    /*留言列表*/
    @ResponseBody
    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public Map<String, Object> message(HttpServletRequest request, HttpSession session) {
        String stuid = (String) session.getAttribute("userid");
        List<MessageInfo> res = istuDao.getStuMessage(stuid);
        return Msg.Success(res);
    }

    /*详细成绩信息*/
    @ResponseBody
    @RequestMapping(value = "/grade_detail", method = RequestMethod.GET)
    public Map<String, Object> grade_detail(HttpServletRequest request, HttpSession session) {
        String stuid = (String) session.getAttribute("userid");
        final List<GradeInfo> grade = istuDao.getStuGrade(stuid);

        Map<String, Object> res = new HashMap<String, Object>() {
            {
                put("grade", grade);
                put("info", new HashMap<String, Object>() {
                    {
                        GradeTool.GBA g = GradeTool.getGBA(grade);
                        put("GBA", g.avgGBA());
                        put("total_credit", g.credit_sum);
                        put("effective_credit", g.GBA_sum);
                    }
                });
            }
        };
        return Msg.Success(res);
    }
}
