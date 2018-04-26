package com.cxgm.domain;

import java.io.Serializable;

public class AdminRole implements Serializable {
	private static final long serialVersionUID = 3455647327017720767L;

	private Long id;

    private Long admins;

    private Long roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdmins() {
        return admins;
    }

    public void setAdmins(Long admins) {
        this.admins = admins;
    }

    public Long getRoles() {
        return roles;
    }

    public void setRoles(Long roles) {
        this.roles = roles;
    }
}