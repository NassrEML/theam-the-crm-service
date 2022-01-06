package com.nassreml.crm.customer.web.request;

import javax.validation.constraints.NotNull;


public class CreateCustomerRequest {

    @NotNull
    private String firstName;
    @NotNull
    private String surname;
    @NotNull
    private String photo;

    public CreateCustomerRequest(String firstName, String surname, String photo) {
        this.firstName = firstName;
        this.surname = surname;
        this.photo = photo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoto() {
        return photo;
    }
}
