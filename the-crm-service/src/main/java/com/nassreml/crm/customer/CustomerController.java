package com.nassreml.crm.customer;

import com.nassreml.crm.customer.web.request.CreateCustomerRequest;
import com.nassreml.crm.customer.web.response.ListCustomersResponse;
import com.nassreml.crm.customer.web.response.GenericWebResponse;
import com.nassreml.crm.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@PreAuthorize("authenticated")
@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final JwtService jwtService;

    @Autowired
    public CustomerController(CustomerService customerService, JwtService jwtService) {
        this.customerService = customerService;
        this.jwtService = jwtService;
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ListCustomersResponse> listCustomers() {
        final ListCustomersResponse customers = new ListCustomersResponse(customerService.getCustomers());
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestHeader(value = "authorization", defaultValue = "") String auth,
                                            @RequestBody @Valid CreateCustomerRequest customerRequest) {
        final String username = jwtService.user(auth);
        final Customer customer = customerService.createCustomer(customerRequest, username);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @PutMapping(path = "{customerId}/image")
    public ResponseEntity<?> uploadImage(@RequestHeader(value = "authorization", defaultValue = "") String auth,
                                         @RequestParam MultipartFile file,
                                         @PathVariable("customerId") Long customerId) {
        try {
            final String url = customerService.uploadImage(file, customerId);

            // IMPORTANT: I need to do that cause Spring don't allow calls to annotated methods inside same class.
            final String username = jwtService.user(auth);
            customerService.updateCustomerUrl(url, customerId, username);
        } catch (IllegalStateException exception) {
            getWebResponse(exception, null, HttpStatus.CONFLICT);
        }

        return getWebResponse(null, "Customer with id " + customerId + " has been updated image",
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @DeleteMapping(path = "{customerId}/image")
    public ResponseEntity<?> deleteImageOfCustomer(@RequestHeader(value = "authorization", defaultValue = "") String auth,
                                                   @PathVariable("customerId") Long customerId) {
        final String username = jwtService.user(auth);
        customerService.deleteCustomerPhoto(customerId, username);

        return getWebResponse(null, "Customer with id " + customerId + " has been removed image",
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @PutMapping(path = "{customerId}")
    public ResponseEntity<?> updateCustomer(@RequestHeader(value = "authorization", defaultValue = "") String auth,
                                            @PathVariable("customerId") Long customerId,
                                            @RequestBody @Valid CreateCustomerRequest customerRequest) {
        try {
            final String username = jwtService.user(auth);
            customerService.updateCustomer(customerId, customerRequest.getFirstName(), customerRequest.getSurname(), username);
        } catch (IllegalStateException exception) {
            getWebResponse(exception, null, HttpStatus.CONFLICT);
        }
        return getWebResponse(null, "Customer with id " + customerId + " updated", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
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
