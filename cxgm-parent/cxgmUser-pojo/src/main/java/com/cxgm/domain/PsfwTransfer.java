package com.cxgm.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PsfwTransfer {

	@ApiModelProperty(name = "psfw",value = "配送范围")
    private String psfw;

}