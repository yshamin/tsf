package client;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.jaxrs.client.WebClient;

import common.Person;

public final class RESTClient {
   private static String urlStem = "http://localhost:8080/services/membershipservice/members/";
   
   public static void main (String[] args) throws Exception {
        getMember(1);

        WebClient wc = WebClient.create(urlStem);
        wc.path("1");
        wc.path("name").type("text/plain");
        Response rc = wc.put("George");              
        getMember(1);
        
        Person newMember = new Person();
        newMember.setName("Harry");
        Response response = wc.reset().post(newMember);
        getMember(2);

        System.out.println("\n");
        System.exit(0);
    } 

    private static void getMember(int memberNo) throws Exception {
       WebClient wc = WebClient.create(urlStem);
       wc.path(memberNo);
       Person p = wc.get(Person.class);
       System.out.println("Person returned: name = " + p.getName() + "; id = " + p.getId());
   }

    private static String getStringFromInputStream(InputStream in) throws Exception {
        CachedOutputStream bos = new CachedOutputStream();
        IOUtils.copy(in, bos);
        in.close();
        bos.close();
        return bos.getOut().toString();
    }
}

