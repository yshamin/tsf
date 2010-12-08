Interceptors Example
==================================================================

This sample demonstrates how a message changes and is manipulated
as it passes through the various interceptors.

This sample consists of 3 parts:
common/   - This directory contains the code that is common
            for both the client and the server.  It contains
	    the WSDL and the artifacts that are generated 
	    from that WSDL.  It also contains a DemoInterceptor
	    that prints out various parts of the message
	    as part of its handleMessage method.

service/  - This is the service.   It adds a DemoInterceptor
            to every phase to show how the service reads
	    and writes the messages it processes.

client/   - This is a sample client application that uses
            the JAX-WS API's to create a proxy client and
	    makes a call with it.  It also adds a 
	    DemoInterceptor to every phase.



Building the Demo
---------------------------------------
  
Using either UNIX or Windows:

    mvn install



Starting the Service
---------------------------------------
  * From the command line:
     cd service ; mvn exec:java

  * From within the OSGi container
     From the OSGi command line, run:
	install mvn:com.talend.sf.examples.jaxws-jms-spec/jms-spec-common/1.0
        install mvn:com.talend.sf.examples.jaxws-jms-spec/jms-spec-server/1.0
     That should print out the bundle ID for the server bundle.  From 
     the OSGi command line, then run
        start 115
     where 115 is the bundle ID number that was printed during install.


Running the Client
---------------------------------------
  * From the command line:
     cd client ; mvn exec:java
  * From within the OSGi container
     From the OSGi command line, run:
	install mvn:com.talend.sf.examples.jaxws-jms-spec/jms-spec-common/1.0
        install mvn:com.talend.sf.examples.jaxws-jms-spec/jms-spec-client/1.0
     That should print out the bundle ID for the server bundle.  From 
     the OSGi command line, then run
        start 115
     where 115 is the bundle ID number that was printed during install.



Cleaning up
---------------------------------------
To remove the code generated from the WSDL file and the .class
files, run "mvn clean".



