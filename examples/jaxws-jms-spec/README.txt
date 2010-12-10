SOAP/JMS Specification Transport Demo using Document-Literal Style
==================================================================

This sample demonstrates use of the Document-Literal style 
binding over the SOAP/JMS Specification Transport.

This sample consists of 3 parts:
common/   - This directory contains the code that is common
            for both the client and the server.  It contains
	    the WSDL and the artifacts that are generated 
	    from that WSDL.  The WSDL uses the SOAP/JMS 
	    specification extensors and URL to define
	    how to connect to the broker and the various
	    QOS parameters to use.

service/  - This is the service.   At startup, it will
            connect to the broker based on the information
	    in the WSDL and register the greeter service.

client/   - This is a sample client application that uses
            the JAX-WS API's to create a proxy client and
	    makes several calls with it.



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
     That should print out the bundle ID for the client bundle.  From 
     the OSGi command line, then run
        start 115
     where 115 is the bundle ID number that was printed during install.



Cleaning up
---------------------------------------
To remove the code generated from the WSDL file and the .class
files, run "mvn clean".



