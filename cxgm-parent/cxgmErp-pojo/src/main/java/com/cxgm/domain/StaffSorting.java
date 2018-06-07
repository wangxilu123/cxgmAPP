package com.cxgm.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StaffSorting {
    private Integer id;

    private Integer adminId;
    
    private Integer shopId;

    private Integer orderId;

    private String status;

    private Date createTime;

   
}