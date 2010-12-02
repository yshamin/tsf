package client;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.jaxrs.client.WebClient;

import common.Person;

public final class RESTClient {
   private static String urlStem = "http://localhost:8080/services/members/";
   
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
        
        // POSTS (creates) are expected to return 201 status if successful 
        if (response.getStatus() != 201) {
        	throw new RuntimeException("Could not add new member.");
        }
        
        //  POSTS (creates) return the new item's URL (containing the server-generated ID)
        //  in the HTTP Location header
        String location = response.getMetadata().getFirst("Location").toString();
        System.out.println("New Member location: " + location);        
        getMember(location);

        System.out.println("\n");
        System.exit(0);
    } 

    private static void getMember(int memberNo) throws Exception {
       WebClient wc = WebClient.create(urlStem);
       wc.path(memberNo);
       Person p = wc.get(Person.class);
       System.out.println("Member returned: name = " + p.getName() + "; id = " + p.getId());
    }

    private static void getMember(String locationURL) throws Exception {
        WebClient wc = WebClient.create(locationURL);
        Person p = wc.get(Person.class);
        System.out.println("Member returned: name = " + p.getName() + "; id = " + p.getId());
     }

    private static String getStringFromInputStream(InputStream in) throws Exception {
        CachedOutputStream bos = new CachedOutputStream();
        IOUtils.copy(in, bos);
        in.close();
        bos.close();
        return bos.getOut().toString();
    }
}

