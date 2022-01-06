package com.nassreml.crm.customer;

import com.nassreml.crm.customer.web.request.CreateCustomerRequest;
import com.nassreml.crm.customer.web.response.ListCustomersResponse;
import com.nassreml.crm.customer.web.response.GenericWebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping(path = "{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable("customerId") Long customerId) {
        Customer customer;
        try {
            customer = customerService.getCustomerById(customerId);
        } catch (IllegalStateException exception) {
            return getWebResponse(exception, null, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CreateCustomerRequest customerRequest) {
        final Customer customer = customerService.createCustomer(customerRequest);
        // TODO: NOT FINISHED, FALTA EL ID DEL CREADOR
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @PutMapping(path = "{customerId}/image")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file,
                                         @PathVariable("customerId") Long customerId) {
        try {
            final String url = customerService.uploadImage(file, customerId);

            // IMPORTANT: I need to do that cause Spring don't allow calls to annotated methods inside same class.
            customerService.updateCustomerUrl(url, customerId);
        } catch (IllegalStateException exception) {
            getWebResponse(exception, null, HttpStatus.CONFLICT);
        }

        // TODO: NOT FINISHED, FALTA EL ID DEL MODIFICADOR
        return getWebResponse(null, "Customer with id " + customerId + " has been updated image",
                HttpStatus.OK);
    }

    @DeleteMapping(path = "{customerId}/image")
    public ResponseEntity<?> deleteImageOfCustomer(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomerPhoto(customerId);

        // TODO: NOT FINISHED, FALTA EL ID DEL MODIFICADOR
        return getWebResponse(null, "Customer with id " + customerId + " has been removed image",
                HttpStatus.OK);
    }

    @PutMapping(path = "{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable("customerId") Long customerId,
                                            @RequestBody @Valid CreateCustomerRequest customerRequest) {
        try {
            customerService.updateCustomer(customerId, customerRequest.getFirstName(), customerRequest.getSurname());
            // TODO: NOT FINISHED, FALTA EL ID DEL CREADOR
        } catch (IllegalStateException exception) {
            getWebResponse(exception, null, HttpStatus.CONFLICT);
        }
        return getWebResponse(null, "Customer with id " + customerId + " updated", HttpStatus.OK);
    }

    @DeleteMapping(path = "{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomer(customerId);
        return getWebResponse(null, "Customer with id " + customerId + " has been removed",
                HttpStatus.OK);
    }

    private ResponseEntity<GenericWebResponse> getWebResponse(final Exception ex,
                                                              final String message,
                                                              final HttpStatus status) {
        if (ex != null) {
            return new ResponseEntity<>(new GenericWebResponse(ex.getMessage()), status);
        }
        return new ResponseEntity<>(new GenericWebResponse(message), status);
    }

}
