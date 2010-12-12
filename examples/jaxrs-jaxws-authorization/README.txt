JAX-RS JAX-WS Authorization Example 
================================

The demo shows how a single service instance can be exposed as either JAX-RS or JAX-WS
service at the same time and how CXF JAX-RS and JAX-WS proxies can reuse the same code for
invoking on the corresponding emdpoints.

Additionally, it shows how a container-managed authentication can be used to populate a security context and how CXF authorization filters enforce the required authorization rules.

Building the Demo
---------------------------------------

This sample consists of 3 parts:
common/   - This directory contains the code that is common
            for both the client and the server. 
            
service/  - This is where a HelloWorld service implementation shared by JAX-RS and JAX-WS endpoints as well as a JAX-RS authorization filter are located

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
 * To the servlet container:

    cd war; mvn jetty:run

Running the client
---------------------------------------

 * From the command line
   - cd client
   - mvn exec:java

By default, the client will use the http port 8080 for constructing the URIs.
This port value is set during the build in the client.properties resource file. If the server is listening on the alternative port then you can use an 'http.port' system property during the build:
   
- mvn install -Dhttp.port=8181

Demo Desciption
---------------

The goal of the demo is to show how the existing production code can be easily exposed as RESTful service by applying 
several JAX-RS annotations to the interfaces, for example, to HelloWorld interface.

Additionally it shows the CXF security interceptors in action which can be applied to individual endpoints in order to enforce the RBAC authorization rules. 

By following this approach users can experiment with the RESTful approach without necessarily changing the way the existing application
has been designed. 

