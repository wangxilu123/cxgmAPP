package com.cxgm.domain;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HaixinResult {
	
	private String RESULT_CODE;

	private String RESULT_MSG;

	private List<RESULT_DATA> RESULT_DATA ;


}