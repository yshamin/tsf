SOAP/JMS Specification Transport Demo using Document-Literal Style
===============================================

This sample demonstrates use of the Document-Literal style 
binding over SOAP/JMS Specification Transport.

Please review the README in the samples directory before
continuing.

This demo uses ActiveMQ as the JMS implementation for 
illustration purposes only. 

Prerequisite
------------

If your environment already includes cxf-manifest.jar on the
CLASSPATH, and the JDK and ant bin directories on the PATH
it is not necessary to set the environment as described in
the samples directory README.  If your environment is not
properly configured, or if you are planning on using wsdl2java,
javac, and java to build and run the demos, you must set the
environment.


Building and running the demo using maven
---------------------------------------
  
Using either UNIX or Windows:

    mvn install (this will build the demo)

    In separate command windows/shells:
    mvn -Pjms.broker
    cd service ; mvn exec:run
    cd client ; mvn exec:run

To remove the code generated from the WSDL file and the .class
files, run "mvn clean".



