package com.pro.ssm.service;

import com.pro.ssm.model.Message;

import java.util.List;

public interface MsgService {
    List<Message> selectUnReply(int num);

    //如果不存在相应留言返回false,存在返回true且会覆盖已有留言
    boolean replyMessage(int message_id,String content);
}
