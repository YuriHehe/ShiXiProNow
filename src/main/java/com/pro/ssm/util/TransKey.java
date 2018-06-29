package com.pro.ssm.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TransKey {
    public static Map<String, Object> trans(Object obj, Map<String, String> trans_map) {
        Map<String, Object> res = new HashMap<String, Object>();
        if (obj == null) return res;
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                System.out.println(f.getName() + "  " + f.get(obj));
                if (trans_map.containsKey(f.getName())) {
                    res.put(trans_map.get(f.getName()), f.get(obj));
                } else {
                    res.put(f.getName(), f.get(obj));
                }
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return res;
    }
}
