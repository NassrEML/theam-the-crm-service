package com.nassreml.crm.customer.web.request;

import javax.validation.constraints.Size;


public class CreateCustomerRequest {

    @Size(min = 2, max = 200, message = "firstName must be between 2 and 200 characters")
    private String firstName;
    @Size(min = 2, max = 200, message = "surname must be between 2 and 200 characters")
    private String surname;

    public CreateCustomerRequest(final String firstName, final String surname) {
        this.firstName = firstName;
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }
}
