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
package client;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.ResponseExceptionMapper;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;

import com.example.customerservice.Customer;
import com.example.customerservice.CustomerService;
import com.example.customerservice.CustomerServiceService;
import com.example.customerservice.CustomerType;
import com.example.customerservice.NoSuchCustomerException;

public class CustomerServiceClient {
	
	private static final String PORT_PROPERTY = "http.port";
	private static final int DEFAULT_PORT_VALUE = 8080;
	
	private static final String HTTP_PORT;
	static {
		Properties props = new Properties();
		try {
		    props.load(CustomerServiceClient.class.getResourceAsStream("/client.properties"));
		} catch (Exception ex) {
		    throw new RuntimeException("client.properties resource is not available");
		}
		HTTP_PORT = props.getProperty(PORT_PROPERTY);
	} 
	
	int port;
	
    public CustomerServiceClient() {
    	port = getPort();
    }
    
    public void useCustomerServiceSoap(String args[]) throws Exception {
    	CustomerServiceService customerServiceService;
        if (args.length != 0 && args[0].length() != 0) {
            File wsdlFile = new File(args[0]);
            URL wsdlURL;
            if (wsdlFile.exists()) {
                wsdlURL = wsdlFile.toURL();
            } else {
                wsdlURL = new URL(args[0]);
            }
            // Create the service client with specified wsdlurl
            customerServiceService = new CustomerServiceService(wsdlURL);
        } else {
            // Create the service client with its default wsdlurl
            customerServiceService = new CustomerServiceService();
        }

        System.out.println("Using SOAP CustomerService");
                
        CustomerService customerService = customerServiceService.getCustomerServicePort();
        
        Customer customer = createCustomer("Barry");
        customerService.updateCustomer(customer);
        customer = customerService.getCustomerByName("Barry");
        System.out.println(customer.getName());
        try {
        	customerService.getCustomerByName("Smith");
        	throw new RuntimeException("Exception is expected");
        } catch (NoSuchCustomerException ex) {
        	System.out.println("NoSuchCustomerException : Smith");
        }
    }
    
    public void useCustomerServiceRest(String args[]) throws Exception {
    	JAXBElementProvider provider = new JAXBElementProvider();
        provider.setUnmarshallAsJaxbElement(true);
        provider.setMarshallAsJaxbElement(true);
        
        List<Object> providers = new ArrayList<Object>();
        providers.add(provider);
        providers.add(new ResponseExceptionMapper<NoSuchCustomerException>() {

			@Override
			public NoSuchCustomerException fromResponse(Response r) {
				return new NoSuchCustomerException();
			}
        	
        });
        
        
        CustomerService customerService = JAXRSClientFactory.createFromModel(
        		"http://localhost:" + port + "/services/rest", 
        		CustomerService.class, 
        		"classpath:/CustomerService-jaxrs.xml", 
        		providers, 
        		null);
        
        System.out.println("Using RESTful CustomerService");
        
        Customer customer = createCustomer("Smith");
        customerService.updateCustomer(customer);
        
        customer = customerService.getCustomerByName("Smith");
        System.out.println(customer.getName());
        
        customer = customerService.getCustomerByName("Barry");
        if (customer != null) {
        	throw new RuntimeException("Barry should not be found");
        }
        System.out.println("Status : " 
        		+ WebClient.client(customerService).getResponse().getStatus());
        
        try {
        	customerService.getCustomerByName("John");
        	throw new RuntimeException("Exception is expected");
        } catch (NoSuchCustomerException ex) {
        	System.out.println("NoSuchCustomerException : John");
        }
    }
    
    private Customer createCustomer(String name) {
    	Customer cust = new Customer();
        cust.setName(name);
        cust.getAddress().add("Pine Street 200");
        Date bDate = new GregorianCalendar(2009, 01, 01).getTime();
        cust.setBirthDate(bDate);
        cust.setNumOrders(1);
        cust.setRevenue(10000);
        cust.setTest(new BigDecimal(1.5));
        cust.setType(CustomerType.BUSINESS);
        return cust;
    }
    
    private static int getPort() { 
    	try {
    		return Integer.valueOf(HTTP_PORT);
    	} catch (NumberFormatException ex) {
    		// ignore
    	}
    	return DEFAULT_PORT_VALUE;
    }
    
    public static void main(String args[]) throws Exception {
        CustomerServiceClient client = new CustomerServiceClient();
        client.useCustomerServiceSoap(args);
        client.useCustomerServiceRest(args);
        System.exit(0); 
    }
}
