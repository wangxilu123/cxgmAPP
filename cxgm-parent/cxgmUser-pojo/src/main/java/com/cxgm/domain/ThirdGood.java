package com.cxgm.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class ThirdGood implements Serializable{

	private String PLUCODE;

    private String ORGCODE;
    
    private String OJPRICE;
    
    private String PRICE;
    
    private String HYPRICE;
    
    private String PSPRICE;
    
    private String PFPRICE;
    
    private String COUNTS;
    
    private String TOPSTOCK;
    
    private String LOWSTOCK;
    
    private String JHCYCLE;
    
    private String PSCYCLE;
    
    private String ISJH;
    
    private String ISTH;
    
    private String ISPS;
    
    private String ISSALE;
    
    private String ISSALETH;
    
    private String CGMODE;
    
    private String OPFLAG;
    
}
