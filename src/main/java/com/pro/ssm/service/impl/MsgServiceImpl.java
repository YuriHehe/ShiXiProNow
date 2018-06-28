package com.pro.ssm.service.impl;

import com.pro.ssm.dao.MessageMapper;
import com.pro.ssm.model.Message;
import com.pro.ssm.model.MessageWithBLOBs;
import com.pro.ssm.service.MsgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MsgServiceImpl implements MsgService {
    @Resource
    MessageMapper msgDao;

    public List<Message> selectUnReply(int num) {
        return msgDao.selectUnReply(num);
    }

    public boolean replyMessage(int message_id, String content) {
        if(msgDao.selectByPrimaryKey(message_id) == null)return false;
        MessageWithBLOBs tmp = new MessageWithBLOBs();
        tmp.setMsgId(message_id);
        Date date = new Date();
        tmp.setReplyTime(date);
        tmp.setReply(content);
        msgDao.updateByPrimaryKeySelective(tmp);
        return true;
    }
}
