package com.cxgm.dao;

import com.cxgm.domain.PushMessage;
import com.cxgm.domain.PushMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PushMessageMapper {
    long countByExample(PushMessageExample example);

    int deleteByExample(PushMessageExample example);

    int insert(PushMessage record);

    int insertSelective(PushMessage record);

    List<PushMessage> selectByExample(PushMessageExample example);

    int updateByExampleSelective(@Param("record") PushMessage record, @Param("example") PushMessageExample example);

    int updateByExample(@Param("record") PushMessage record, @Param("example") PushMessageExample example);
}