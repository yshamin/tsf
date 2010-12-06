JAX-RS JAX-WS Java First Example 
================================

The demo shows how a single service instance can be exposed as either JAX-RS or JAX-WS
service at the same time and how CXF JAX-RS and JAX-WS proxies can reuse the same code for
invoking on the corresponding emdpoints.


Building the Demo
---------------------------------------

This sample consists of 3 parts:
common/   - This directory contains the code that is common
            for both the client and the server. 
            
service/  - This is where a HelloWorld service implementation shared by JAX-RS and JAX-WS endpoints is located

war/      - This module creates a WAR archive containing the code from common and service modules.   

client/   - This is a sample client application that shows how CXF JAX-RS and JAX-WS proxies are invoking on remote 
            JAX-RS and JAX-WS endpoints represented by HelloWorld interface 


From the base directory of this sample (i.e., where this README file is
located), the maven pom.xml file can be used to build and run the demo. 


Using either UNIX or Windows:

    mvn install

Running this command will build the demo and create a WAR archive and an OSGI bundle 
for deploying the service either to servlet or OSGI containers.

Deploying the service
---------------------------------------
 * To the servlet container

    cd war; mvn jetty:run

 * To the OSGI container

    From the OSGi command line, run:
	install mvn:com.talend.sf.examples/jaxrs-jaxws-description-first-common/1.0
        install mvn:com.talend.sf.examples/jaxrs-jaxws-description-first-service/1.0
     That should print out the bundle IDs for the common and server bundles. From 
     the OSGi command line, then start the installed bundles, for example
        start 115
     where 115 is the bundle ID number that was printed during install.

Running the client
---------------------------------------

 * From the command line
   - cd client
   - mvn exec:java
     or
   - mvn install -Dhttp.port=8181

Demo Desciption
---------------

The goal of the demo is to show how the existing production code can be easily exposed as RESTful service by applying 
several JAX-RS annotations to the interfaces, for example, to HelloWorld interface. 

By following this approach users can experiment with the RESTful approach without necessarily changing the way the existing application
has been designed. 

