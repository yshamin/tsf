/**
 * Copyright (C) 2010 Talend Inc. - www.talend.com
 */
package server;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Endpoint;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;

import com.example.customerservice.CustomerService;

public class CustomerServiceServer {

    protected CustomerServiceServer() throws Exception {

    }

    public static void main(String args[]) throws Exception {

        System.out.println("Starting Server");
        CustomerService implementor = new CustomerServiceImpl();
        Endpoint.publish("http://localhost:8080/services/soap", implementor);

        JAXRSServerFactoryBean jaxrsFactory = new JAXRSServerFactoryBean();
        jaxrsFactory.setAddress("http://localhost:8080/services/rest");
        jaxrsFactory.setModelRef("classpath:/model/CustomerService-jaxrs.xml");
        jaxrsFactory.setServiceBean(implementor);

        List<Object> providers = new ArrayList<Object>();

        JAXBElementProvider jaxbProvider = new JAXBElementProvider();
        jaxbProvider.setMarshallAsJaxbElement(true);
        jaxbProvider.setUnmarshallAsJaxbElement(true);

        providers.add(jaxbProvider);
        providers.add(new NoCustomerExceptionMapper());

        jaxrsFactory.setProviders(providers);

        jaxrsFactory.create();

        System.out.println("Server ready...");
        Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }
}
