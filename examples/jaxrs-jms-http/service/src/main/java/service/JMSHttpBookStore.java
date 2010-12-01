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

package service;


import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.cxf.jaxrs.ext.Oneway;
import org.apache.cxf.jaxrs.ext.ProtocolHeaders;
import org.apache.cxf.transport.jms.JMSUtils;

import common.Book;

@Path("/")
@Produces({"text/xml", "application/xml" })
@Consumes({"text/xml", "application/xml" })
public class JMSHttpBookStore {

    @javax.ws.rs.core.Context
    private ProtocolHeaders context;
    
    private Map<Long, Book> books = new HashMap<Long, Book>();

    public JMSHttpBookStore() {
        books.put(123L, new Book("JMS and HTTP Transport in CXF JAX-RS", 123L));
    }
    

    @GET
    @Path("{bookId}")
    public Book getBook(@PathParam("bookId") Long id) {
        return books.get(id);
    }
    
    @POST
    public Response addBook(Book book) {
        
        book.setId(124);
        books.put(book.getId(), book);

        return Response.ok(book).build();
    }
    
    @PUT
    @Oneway
    public void oneWayRequest(Book book) throws Exception {
        
    	printRequestTransport();
    	
    	Connection connection = null;
    	try {
    		Context ctx = getContext();
            ConnectionFactory factory = (ConnectionFactory)ctx.lookup("ConnectionFactory");
            Destination replyToDestination = (Destination)ctx.lookup("dynamicQueues/test.jmstransport.response");
            connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            postOneWayBook(session, replyToDestination, book);
            session.close();
        } catch (Exception ex) {
        	ex.printStackTrace();
        	throw ex;
        } finally {
            try {
            	if (connection != null) {
	                connection.stop();
	                connection.close();
            	}
            } catch (JMSException ex) {
                // ignore
            }
        }    
    }
    
    private Context getContext() throws Exception {
    	ClassLoader loader = Thread.currentThread().getContextClassLoader();
    	try {
    		Properties props = new Properties();
	        props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
	                          "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
	        props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
	        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
	        return new InitialContext(props);
    	} finally {
    		Thread.currentThread().setContextClassLoader(loader);
    	}
        
    }
    
    private void postOneWayBook(Session session, Destination destination, Book book) 
        throws Exception {
        MessageProducer producer = session.createProducer(destination);
        
        Message message = JMSUtils.createAndSetPayload(
            writeBook(book), session, "text");
                    
        producer.send(message);
        producer.close();
    }
    
    private String writeBook(Book b) throws Exception {
        JAXBContext c = JAXBContext.newInstance(new Class[]{Book.class});
        Marshaller m = c.createMarshaller();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        m.marshal(b, bos);
        return bos.toString();
    }
    
    private void printRequestTransport() {
    	if (context.getRequestHeaderValue("JMSMessageType") != null) {
    		System.out.println("It is the JMS Request");
    	} else {
    		System.out.println("It is the HTTP Request");
    	}
    }
}


