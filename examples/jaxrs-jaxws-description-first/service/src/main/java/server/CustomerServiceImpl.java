/**
 * Copyright (C) 2010 Talend Inc. - www.talend.com
 */
package server;

import java.util.ArrayList;
import java.util.List;

import com.example.customerservice.Customer;
import com.example.customerservice.CustomerService;
import com.example.customerservice.NoSuchCustomer;
import com.example.customerservice.NoSuchCustomerException;

public class CustomerServiceImpl implements CustomerService {

    private volatile Customer customer;

    public List<Customer> getCustomersByName(String name) throws NoSuchCustomerException {
        Customer customer = getCustomerByName(name);

        List<Customer> customers = new ArrayList<Customer>();
        customers.add(customer);

        return customers;
    }

    public Customer updateCustomer(Customer newCustomer) {
        this.customer = newCustomer;
        return customer;
    }

    public Customer getCustomerByName(String name) throws NoSuchCustomerException {

        if (name.equals("John")) {
            throw new RuntimeException("John is not available");
        }

        if (!name.equals(customer.getName())) {
            NoSuchCustomer noSuchCustomer = new NoSuchCustomer();
            noSuchCustomer.setCustomerName(name);
            throw new NoSuchCustomerException("Did not find any matching customer for name=" + name,
                                              noSuchCustomer);
        }

        return customer;
    }
}
