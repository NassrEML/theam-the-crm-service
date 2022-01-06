package com.nassreml.crm.customer;

import com.nassreml.crm.customer.web.request.CreateCustomerRequest;
import com.nassreml.crm.customer.web.response.ListCustomersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<ListCustomersResponse> listCustomers() {
        final ListCustomersResponse customers = new ListCustomersResponse(customerService.getCustomers());
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CreateCustomerRequest customer) {
        //Long my_logged_id = 0L;
        //customerService.createCustomer(customer, my_logged_id, )
        // TODO: NOT FINISHED
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}
