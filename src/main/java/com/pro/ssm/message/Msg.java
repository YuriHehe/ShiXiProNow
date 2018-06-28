package com.pro.ssm.message;

import com.pro.ssm.enumcase.CodeType;
import com.sun.org.apache.bcel.internal.classfile.Code;

import java.util.HashMap;
import java.util.Map;

public class Msg {
    static public Map<String,Object> Error(String msg){
        Map<String,Object> res = new HashMap<String, Object>();
        res.put("code", CodeType.ERROR);
        res.put("msg", msg);
        res.put("data", null);
        return res;
    }
    static public Map<String,Object> Success(String msg, Object data){
        Map<String,Object> res = new HashMap<String, Object>();
        res.put("data", data);
        res.put("code", CodeType.SUCCESS);
        res.put("msg", msg);
        return res;
    }
    static public Map<String,Object> NotLoginError(){
        Map<String,Object> res = new HashMap<String, Object>();
        res.put("data", null);
        res.put("code", CodeType.UNLOGIN);
        res.put("msg", "未登陆错误");
        return res;
    }
    static public Map<String,Object> Unfinished(){
        Map<String,Object> res = new HashMap<String, Object>();
        res.put("data", null);
        res.put("code", CodeType.UNFINISHED);
        res.put("msg", "该模块施工中");
        return res;
    }
}
