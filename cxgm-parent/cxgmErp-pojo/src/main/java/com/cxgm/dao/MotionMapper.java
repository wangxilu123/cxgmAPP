package com.cxgm.dao;

import com.cxgm.domain.Motion;
import com.cxgm.domain.MotionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MotionMapper {
    long countByExample(MotionExample example);

    int deleteByExample(MotionExample example);

    int insert(Motion record);

    int insertSelective(Motion record);

    List<Motion> selectByExample(MotionExample example);

    int updateByExampleSelective(@Param("record") Motion record, @Param("example") MotionExample example);

    int updateByExample(@Param("record") Motion record, @Param("example") MotionExample example);
}