package com.pro.ssm.dao;

import com.pro.ssm.model.Message;
import com.pro.ssm.model.MessageWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(MessageWithBLOBs record);

    int insertSelective(MessageWithBLOBs record);

    MessageWithBLOBs selectByPrimaryKey(Integer msgId);

    int updateByPrimaryKeySelective(MessageWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(MessageWithBLOBs record);

    int updateByPrimaryKey(Message record);

    List<Message> selectUnReply(@Param("num")int num);
}