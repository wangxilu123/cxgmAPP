package com.cxgm.dao;

import com.cxgm.domain.StaffSorting;
import com.cxgm.domain.StaffSortingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StaffSortingMapper {
    long countByExample(StaffSortingExample example);

    int deleteByExample(StaffSortingExample example);

    int insert(StaffSorting record);

    int insertSelective(StaffSorting record);

    List<StaffSorting> selectByExample(StaffSortingExample example);

    int updateByExampleSelective(@Param("record") StaffSorting record, @Param("example") StaffSortingExample example);

    int updateByExample(@Param("record") StaffSorting record, @Param("example") StaffSortingExample example);
}