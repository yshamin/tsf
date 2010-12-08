package demo.interceptors.client;

import java.net.URL;

import javax.xml.namespace.QName;

import com.talend.examples.interceptors.Greeter;
import com.talend.examples.interceptors.GreeterService;

import demo.interceptors.interceptor.DemoInterceptor;

import org.apache.cxf.frontend.ClientProxy;


public final class Client {

    private static final QName SERVICE_NAME =
        new QName("http://talend.com/examples/interceptors", "GreeterService");
    private static final QName PORT_NAME =
        new QName("http://talend.com/examples/interceptors", "GreeterPort");

    public Client() throws Exception {
        this(new String[0]);
    }
    public Client(String args[]) throws Exception {

        URL wsdl = null;
        if (args.length == 0) {
            wsdl = Client.class.getResource("/interceptors-wsdl/hello_world.wsdl");
        }

        GreeterService service = new GreeterService(wsdl, SERVICE_NAME);
        Greeter greeter = (Greeter)service.getPort(PORT_NAME, Greeter.class);

        //Use CXF API's to grab the underlying Client object and add
        //the DemoInterceptor's to it
        org.apache.cxf.endpoint.Client client = ClientProxy.getClient(greeter);
        DemoInterceptor.addInterceptors(client);
        
        System.out.println("Invoking greetMe...");
        System.out.println("server responded with: " + greeter.greetMe(System.getProperty("user.name")));
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        new Client(args);
        System.exit(0);
    }
}
