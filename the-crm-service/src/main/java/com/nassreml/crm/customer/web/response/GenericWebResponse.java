package com.nassreml.crm.customer.web.response;

public class GenericWebResponse {
    private final String message;

    public GenericWebResponse(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
