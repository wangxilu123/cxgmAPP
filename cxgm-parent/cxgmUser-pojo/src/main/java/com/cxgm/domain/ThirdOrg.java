package com.cxgm.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class ThirdOrg implements Serializable{

	private String ORGCODE;

    private String ORGNAME;
    
    private String FORGCODE;
    
    private String ORGTYPE;
    
    private String INORGCODE;
    
    private String ZBORGCODE;
    
    private List<String> REMARK;
    
    private String USEEPT;
    
}
