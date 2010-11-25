JAX-RS Advanced Example 
===========================

The demo shows some of the major features the JAX-RS 1.1 specification [1] and API [2] provide :

- Multiple JAX-RS root resource classes
- Recursive JAX-RS SubResourses
- Resource methods consuming and producing data in different formats (XML and JSON)
- Various HTTP verbs in action
- How to use JAX-RS Response [2] to return a status, headers and optional entities
- How to use JAX-RS UriInfo[2] and UriBuilder[2] for returing the links to newly created resources
- JAX-RS ExceptionMappers[2] for handling exceptions thrown from the application code

Additionally HTTP Centric and Proxy-based Apache CXF JAX-RS client API is demonstrated.

[1] http://jcp.org/aboutJava/communityprocess/mrel/jsr311/index.html
[2] https://jsr311.dev.java.net/nonav/releases/1.1/index.html

Building the Demo
---------------------------------------

This sample consists of 3 parts:
common/   - This directory contains the code that is common
            for both the client and the server. 
            
service/  - This is the JAX-RS service with multiple root resources packaged as an OSGI bundle.
             

war/      - This module creates a WAR archive containing the code from common and service modules.   

client/   - This is a sample client application that uses
            the CXF JAX-RS API to create HTTP-centric and proxy clients and
	    makes several calls with them.


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
	install mvn:com.talend.sf.examples.jaxrs-advanced-common/jaxrs-advanced-common/1.0
        install mvn:com.talend.sf.examples.jaxrs-advanced-service-bundle/jaxrs-advanced-service-bundle/1.0
     That should print out the bundle IDs for the common and server bundles.  From 
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

JAX-RS Server provides two services via the registration of multiple (two) root resource classes, 
PersonService and SearchService with both services sharing the data storage.

PersonService provides information about all the persons it knows about, about individual persons and their relatives :
- ancestors - parents, grandparents, etc
- descendants - children, etc
- partners

Additionally it can help with adding the information about new children 
to existing persons and update the age of the current Person.

SearchService is a simple service which shares the information about Persons with the PersonService. 
It lets users search for individual people by specifying one or more names as query parameters. The interaction with 
this service also verifies that the JAX-RS server is capable of supporting 
multiple root resource classes.

RESTful client uses CXF JAX-RS WebClient to traverse all the information about an individual Person and also adds a new child.
It also shows how to use a simple proxy.

Finally a simple proxy is created and is used to make the calls.

Please check the comment in the code for a detailed description on how client calls are made and 
how they are processed on the server side.

