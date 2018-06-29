package com.pro.ssm.util;

import java.util.HashMap;
import java.util.Map;

public class Msg {

    public enum CodeType {
        SUCCESS(200), ERROR(1000), UNLOGIN(100), UNFINISHED(99999);

        private int value;

        private CodeType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    static public Map<String, Object> Error(String msg) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("code", CodeType.ERROR.getValue());
        res.put("msg", msg);
        res.put("data", null);
        return res;
    }

    static public Map<String, Object> Success(String msg, Object data) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("data", data);
        res.put("code", CodeType.SUCCESS.getValue());
        res.put("msg", msg);
        return res;
    }

    static public Map<String, Object> Success(Object data) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("data", data);
        res.put("code", CodeType.SUCCESS.getValue());
        res.put("msg", "");
        return res;
    }

    static public Map<String, Object> Success() {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("data", null);
        res.put("code", CodeType.SUCCESS.getValue());
        res.put("msg", "");
        return res;
    }

    static public Map<String, Object> NotLoginError() {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("data", null);
        res.put("code", CodeType.UNLOGIN.getValue());
        res.put("msg", "未登陆错误");
        return res;
    }

    static public Map<String, Object> Unfinished() {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("data", null);
        res.put("code", CodeType.UNFINISHED.getValue());
        res.put("msg", "该模块施工中");
        return res;
    }

}
