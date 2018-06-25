package com.cxgm.domain;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RESULT_DATA
{
    private List<GOODS_DATA> GOODS_DATA;

    public void setGOODS_DATA(List<GOODS_DATA> GOODS_DATA){
        this.GOODS_DATA = GOODS_DATA;
    }
    public List<GOODS_DATA> getGOODS_DATA(){
        return this.GOODS_DATA;
    }
}
