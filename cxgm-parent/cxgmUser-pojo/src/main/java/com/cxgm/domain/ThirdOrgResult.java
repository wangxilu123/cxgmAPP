package com.cxgm.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ThirdOrgResult implements Serializable{

    private String RESULT_CODE;

    private String RESULT_MSG;
    
    private List<ThirdOrg> RESULT_DATA;
}
