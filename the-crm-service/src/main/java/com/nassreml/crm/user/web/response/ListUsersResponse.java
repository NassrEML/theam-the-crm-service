package com.nassreml.crm.user.web.response;

import com.nassreml.crm.user.User;

import java.util.List;

public class ListUsersResponse {
    private final List<User> users;
    private final Integer numberOfUsers;

    public ListUsersResponse(final List<User> users) {
        this.users = users;
        this.numberOfUsers = 0;
    }

    public List<User> getUsers() {
        return users;
    }

    public Integer getNumberOfUsers() {
        return users.size();
    }
}
