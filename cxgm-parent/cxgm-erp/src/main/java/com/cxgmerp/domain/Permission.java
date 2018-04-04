package com.cxgmerp.domain;

import java.io.Serializable;
import java.util.Date;

public class Permission implements Serializable{
	private static final long serialVersionUID = -7526150743688102557L;

	private Long id;

	private String name;

	private String value;

	private String url;

	private String module;

	private String description;

	private String createBy;

	private Date creationDate;

	private String lastUpdatedBy;

	private Date lastUpdatedDate;

	private Boolean deleteFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value == null ? null : value.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module == null ? null : module.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy == null ? null : lastUpdatedBy.trim();
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (obj instanceof Permission) {
			Permission user = (Permission) obj;
			if (user.id == this.id)
				return true; // 只比较id
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = (int) (31 * result + this.id.hashCode());
		return result;
	}
}