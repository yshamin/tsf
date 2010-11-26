JAX-RS JMS HTTP Demo 
===============================================

This sample demonstrates how a JAX-RS HTTP server can be enhanced to receive JMS messages

This sample consists of 4 parts:
common/   - This directory contains the code that is common
            for both the client and the server.  It contains
	    a single Book class.

service/  - This is the service.   At startup, it will
            connect to the JMS broker.

client/   - This is a sample client application that uses
            the CXF JAX-RS API and pure JMS API's

war/      - This is a war achive.

This demo uses ActiveMQ as the JMS implementation for 
illustration purposes only. 


Building the Demo
---------------------------------------
  
Using either UNIX or Windows:

    mvn install


Running the JMS Broker
---------------------------------------
The sample requires a JMS broker to be running.  There are two
ways to get a JMS broker running:

 * From the command line
     In separate command windows/shells:
     mvn -Pjms.broker

 * From within the OSGi container
     From the OSGi command line, run:
         activemq:create-broker 
     That will create a new broker broker with the defaults and 
     will then start it.


Deploying the service
---------------------------------------
 * To the servlet container

    cd war; mvn jetty:run

 * To the OSGI container

    From the OSGi command line, run:
	install mvn:com.talend.sf.examples.jaxrs-jms-http-common/jaxrs-jms-http-common/1.0
        install mvn:com.talend.sf.examples.jaxrs-jms-http-service/jaxrs-jms-http-service/1.0
     That should print out the bundle IDs for the common and server bundles. From 
     the OSGi command line, then start the installed bundles, for example
        start 115
     where 115 is the bundle ID number that was printed during install.


Running the Client
---------------------------------------
  * From the command line:
     cd client ; mvn exec:java


Demo Description
---------------------------------------

This demo demonstrates how JAX-RS HTTP servers can be enhanced to get JMS messages.

JAX-RS is the HTTP centric specification and it is likely developers will be starting from
working upon JAX-RS HTTP servers. However given the popularity of JMS the new requirements for the the existing
JAX-RS servers receive messages over JMS will likely need to be met.

This demo deliberately uses a war archive to emulate the possible development process where a JAX-RS HTTP server
is created first and at the next stage a CXF JAX-RS JMS endpoint is created with the same service instance being shared
between the two endpoints.

The demo demonstrates how both CXF JAX-RS WebClients and pure JMS clients can interact with HTTP and JMS endpoints.
It also shows how oneway HTTP requests can be further routed to JMS destinations.





