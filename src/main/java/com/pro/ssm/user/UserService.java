package com.pro.ssm.user;

import java.util.Map;

public interface UserService {
    //检查用户是否存在,以及密码是否匹配
    Map<String,Object> userCheck(String userid, String password, String role);

    //更改密码,需要提供原密码和角色类型
    Map<String,Object> change_password(String userid, String role, String oldpsd, String newpsd);
}
