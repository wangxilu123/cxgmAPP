package com.cxgm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cxgm.domain.FjpsHomePage;
import com.cxgm.domain.StaffDistribution;
import com.cxgm.domain.StaffDistributionExample;

public interface StaffDistributionMapper {
    long countByExample(StaffDistributionExample example);

    int deleteByExample(StaffDistributionExample example);

    int insert(StaffDistribution record);

    int insertSelective(StaffDistribution record);

    List<StaffDistribution> selectByExample(StaffDistributionExample example);

    int updateByExampleSelective(@Param("record") StaffDistribution record, @Param("example") StaffDistributionExample example);

    int updateByExample(@Param("record") StaffDistribution record, @Param("example") StaffDistributionExample example);
    
    FjpsHomePage  selectHomePageNum (Map<String,Object> map);
}