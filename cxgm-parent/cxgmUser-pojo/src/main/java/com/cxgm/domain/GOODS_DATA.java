package com.cxgm.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class GOODS_DATA implements Serializable{

	private List<ThirdGood> thirdGoodList;
    
}
