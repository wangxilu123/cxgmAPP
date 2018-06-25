package com.cxgm.domain;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class ThirdGood
{
    private String RESULT_CODE;

    private String RESULT_MSG;

    private String RESULT_HASNEXT;

    private String RESULT_INCNO;

    private RESULT_DATA RESULT_DATA;

    public void setRESULT_CODE(String RESULT_CODE){
        this.RESULT_CODE = RESULT_CODE;
    }
    public String getRESULT_CODE(){
        return this.RESULT_CODE;
    }
    public void setRESULT_MSG(String RESULT_MSG){
        this.RESULT_MSG = RESULT_MSG;
    }
    public String getRESULT_MSG(){
        return this.RESULT_MSG;
    }
    public void setRESULT_HASNEXT(String RESULT_HASNEXT){
        this.RESULT_HASNEXT = RESULT_HASNEXT;
    }
    public String getRESULT_HASNEXT(){
        return this.RESULT_HASNEXT;
    }
    public void setRESULT_INCNO(String RESULT_INCNO){
        this.RESULT_INCNO = RESULT_INCNO;
    }
    public String getRESULT_INCNO(){
        return this.RESULT_INCNO;
    }
    public void setRESULT_DATA(RESULT_DATA RESULT_DATA){
        this.RESULT_DATA = RESULT_DATA;
    }
    public RESULT_DATA getRESULT_DATA(){
        return this.RESULT_DATA;
    }
}
