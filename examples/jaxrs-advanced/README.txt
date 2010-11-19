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

This demo is a continious work in progress and the list of demonstrated features will be enhanced.

Additionally HTTP Centric and Proxy-based Apache CXF JAX-RS client API is demonstrated.

[1] http://jcp.org/aboutJava/communityprocess/mrel/jsr311/index.html
[2] https://jsr311.dev.java.net/nonav/releases/1.1/index.html

Building and running the demo using Maven
---------------------------------------

From the base directory of this sample (i.e., where this README file is
located), the maven pom.xml file can be used to build and run the demo. 

The demo contains three modules :
- common
- server-war
- client

Running 'mvn install' will prepare the demo. Next, follow these steps :

1. Go to 'server-war' and do

mvn jetty:run

This will create a war and deploy it into Jetty.

2. Go to 'client' and run the client

mvn exec:java

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

Please check the comment in the code for a detailed description on how client invocations are made and 
how they are processed on the server side.

