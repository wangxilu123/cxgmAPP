package com.cxgm.domain;

import java.util.Date;

public class Version {
    private Integer id;

    private String versionNum;

    private String url;
    
    private Integer sign;
    
    private String iOSVersionNum;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum == null ? null : versionNum.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public String getiOSVersionNum() {
		return iOSVersionNum;
	}

	public void setiOSVersionNum(String iOSVersionNum) {
		this.iOSVersionNum = iOSVersionNum;
	}
    
}