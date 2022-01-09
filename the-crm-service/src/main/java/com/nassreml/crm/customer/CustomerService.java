package com.nassreml.crm.customer;

import com.nassreml.crm.customer.web.request.CreateCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {


    private final CustomerRepository customerRepository;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CloudinaryService cloudinaryService) {
        this.customerRepository = customerRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(final Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent()) {
            throw new IllegalStateException("The customer with id " + id + " doesn't exists");
        }
        return customer.get();
    }

    public Customer createCustomer(final CreateCustomerRequest customerRequest, final String username) {
        final Customer customer = new Customer();
        customer.setFirstName(customerRequest.getFirstName());
        customer.setSurname(customerRequest.getSurname());
        customer.setWhoCreate(username);
        return customerRepository.save(customer);
    }

    public String uploadImage(final MultipartFile file, final Long customerId) {
        boolean customerExists = customerRepository.existsById(customerId);
        if (!customerExists) {
            throw new IllegalStateException("The user with id " + customerId + " doesn't exists");
        }

        String url;
        try {
            url = cloudinaryService.uploadFile(file);
        } catch (RuntimeException exception) {
            throw new IllegalStateException("Cannot upload image, try it later :(");
        }
        return url;
    }

    @Transactional
    public void updateCustomerUrl(final String url, final Long customerId, final String username) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        customerOptional.get().setPhoto(url);
        customerOptional.get().setWhoWasTheLastToModify(username);
    }

    @Transactional
    public void deleteCustomerPhoto(final Long customerId, final String username) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        customerOptional.get().setPhoto(null);
        customerOptional.get().setWhoWasTheLastToModify(username);
    }

    @Transactional
    public void updateCustomer(final Long customerId, final String firstName, final String surname, final String username) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (!customer.isPresent()) {
            throw new IllegalStateException("The customer with id " + customerId + " doesn't exists");
        }
        customer.get().setFirstName(firstName);
        customer.get().setSurname(surname);
        customer.get().setWhoWasTheLastToModify(username);
    }

    public void deleteCustomer(final Long customerId) {
        customerRepository.deleteById(customerId);
    }
}
