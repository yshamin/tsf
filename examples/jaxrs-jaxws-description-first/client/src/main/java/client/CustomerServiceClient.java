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
import java.net.URL;
import java.util.Collections;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;

import com.example.customerservice.Customer;
import com.example.customerservice.CustomerService;
import com.example.customerservice.CustomerServiceService;

public class CustomerServiceClient {
    protected CustomerServiceClient() {
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

        CustomerService customerService = customerServiceService.getCustomerServicePort();
        Customer customer = customerService.getCustomerByName("Barry");
        System.out.println(customer.getName());
        
    }
    
    public void useCustomerServiceRest(String args[]) throws Exception {
    	JAXBElementProvider provider = new JAXBElementProvider();
        provider.setUnmarshallAsJaxbElement(true);
        
        CustomerService service = JAXRSClientFactory.createFromModel(
        		"http://localhost:8080/services/rest", 
        		CustomerService.class, 
        		"classpath:/CustomerService-jaxrs.xml", 
        		Collections.singletonList(provider), 
        		null);
        
        Customer customer = service.getCustomerByName("Smith");
        System.out.println(customer.getName());
        
    }
    
    public static void main(String args[]) throws Exception {
        CustomerServiceClient client = new CustomerServiceClient();
        System.out.println("Using SOAP CustomerService");
        client.useCustomerServiceSoap(args);
        System.out.println("Using RESTful CustomerService");
        client.useCustomerServiceRest(args);
        System.exit(0); 
    }
}
