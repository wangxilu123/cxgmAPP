package com.cxgm.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FjpsHomePage {
	
	@ApiModelProperty(name = "todayDfjNum",value = "今日待分拣数量")
    private Integer todayDfjNum;
    
	@ApiModelProperty(name = "todayWfjNum",value = "今日完成分拣数量")
    private Integer todayWfjNum;

	@ApiModelProperty(name = "weekWfjNum",value = "本周完成分拣数量")
    private Integer weekWfjNum;

	@ApiModelProperty(name = "monthWfjNum",value = "本月完成分拣数量")
    private Integer monthWfjNum;

	@ApiModelProperty(name = "fjPercentage",value = "分拣完成百分比")
    private String fjPercentage;
	
	
	@ApiModelProperty(name = "todayDpsNum",value = "今日待配送数量")
    private Integer todayDpsNum;
    
	@ApiModelProperty(name = "todayWpsNum",value = "今日完成配送数量")
    private Integer todayWpsNum;

	@ApiModelProperty(name = "weekWpsNum",value = "本周完成配送数量")
    private Integer weekWpsNum;

	@ApiModelProperty(name = "monthWpsNum",value = "本月完成配送数量")
    private Integer monthWpsNum;

	@ApiModelProperty(name = "psPercentage",value = "配送完成百分比")
    private String psPercentage;
    
   
}