package com.cxgmerp.domain;

public class PermissionRole {
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