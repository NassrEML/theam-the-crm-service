package com.nassreml.crm.customer.web.response;

import com.nassreml.crm.customer.Customer;

import java.util.List;

public class ListCustomersResponse {

    private final List<Customer> customers;
    private final Integer numberOfCustomers;

    public ListCustomersResponse(final List<Customer> customers) {
        this.customers = customers;
        this.numberOfCustomers = 0;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Integer getNumberOfCustomers() {
        return customers.size();
    }
}
