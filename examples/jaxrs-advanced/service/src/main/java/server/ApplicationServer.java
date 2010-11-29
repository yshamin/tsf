package server;

import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

public class ApplicationServer {

    protected ApplicationServer() throws Exception {
        PersonApplication application = new PersonApplication();
        RuntimeDelegate delegate = RuntimeDelegate.getInstance();
        
        JAXRSServerFactoryBean bean = delegate.createEndpoint(application, JAXRSServerFactoryBean.class);
        bean.setAddress("http://localhost:8080/services" + bean.getAddress());
        bean.create().start();
    }

    public static void main(String args[]) throws Exception {
        new ApplicationServer();
        System.out.println("Server ready...");

        Thread.sleep(125 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }
}
