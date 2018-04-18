package com.cxgm.domain;

import java.io.Serializable;

public class PermissionRole implements Serializable{
	private static final long serialVersionUID = -6303360743986015371L;

	private Long id;

    private Long permissions;

    private Long roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPermissions() {
        return permissions;
    }

    public void setPermissions(Long permissions) {
        this.permissions = permissions;
    }

    public Long getRoles() {
        return roles;
    }

    public void setRoles(Long roles) {
        this.roles = roles;
    }
}