package com.cxgm.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ThirdGoodResult implements Serializable{

    private String RESULT_CODE;

    private String RESULT_MSG;
    
    private String RESULT_HASNEXT;
    
    private String RESULT_INCNO;
    
    private GOODS_DATA RESULT_DATA;
}
