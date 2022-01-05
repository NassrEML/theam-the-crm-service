package com.nassreml.crm.user.web.request;

public class ChangeAdminRequest {

    final private Long id;
    final private boolean admin;

    public ChangeAdminRequest(final Long id, final boolean admin) {
        this.id = id;
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public boolean isAdmin() {
        return admin;
    }
}
