/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
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

    public void updateCustomer(Customer newCustomer) {
    	System.out.println("Updating customer");
        this.customer = newCustomer;
    }

	public Customer getCustomerByName(String name) throws NoSuchCustomerException {
		
		if (!name.equals(customer.getName())) {
			NoSuchCustomer noSuchCustomer = new NoSuchCustomer();
	        noSuchCustomer.setCustomerName(name);
	        throw new NoSuchCustomerException("Did not find any matching customer for name=" + name,
	                                          noSuchCustomer);
		}
		
		return customer;
	}
}
