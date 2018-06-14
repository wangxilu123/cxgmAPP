package com.cxgm.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Shop {
	
	@ApiModelProperty(name = "id",value = "主键ID",hidden = true)
    private Integer id;

	@ApiModelProperty(name = "shopName",value = "门店名称")
    private String shopName;

	@ApiModelProperty(name = "shopAddress",value = "门店地址")
    private String shopAddress;

	@ApiModelProperty(name = "longitude",value = "门店经度")
    private String longitude;

	@ApiModelProperty(name = "dimension",value = "门店维度")
    private String dimension;

	@ApiModelProperty(name = "imageUrl",value = "门店图片")
    private String imageUrl;

	@ApiModelProperty(name = "description",value = "门店描述")
    private String description;

	@ApiModelProperty(name = "owner",value = "门店负责人")
    private String owner;

	@ApiModelProperty(name = "electronicFence",value = "门店配送范围")
    private String electronicFence;

	@ApiModelProperty(name = "weixinMchid",value = "门店微信商户ID")
    private String weixinMchid;

	@ApiModelProperty(name = "weixinApikey",value = "门店微信APPKEY")
    private String weixinApikey;

	@ApiModelProperty(name = "aliPartnerid",value = "门店支付宝合作和ID")
    private String aliPartnerid;

	@ApiModelProperty(name = "aliPrivatekey",value = "门店支付宝商户私钥")
    private String aliPrivatekey;
	
	@ApiModelProperty(name = "createTime",value = "创建时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	
	@ApiModelProperty(name = "yzShopId",value = "有赞门店ID")
    private Integer yzShopId;
	
	@ApiModelProperty(name = "hxShopId",value = "海信门店ID")
    private Integer hxShopId;
}