package client;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.jaxrs.client.WebClient;

import common.Person;

public final class RESTClient {
   private static String urlStem = "http://localhost:8080/membership/membershipservice";
   
   public static void main (String[] args) throws Exception {

        getMember(1);

        // Sent HTTP PUT request to update member info
        System.out.println("\n");
        System.out.println("Sent HTTP PUT request to update member info");
        RESTClient client = new RESTClient();
        String inputFile = client.getClass().getResource("/UpdateMember.xml").getFile();
        File input = new File(inputFile);
        PutMethod put = new PutMethod(urlStem + "/members");
        RequestEntity entity = new FileRequestEntity(input, "text/xml; charset=ISO-8859-1");
        put.setRequestEntity(entity);
        HttpClient httpclient = new HttpClient();

        try {
            int result = httpclient.executeMethod(put);
            System.out.println("Response status code: " + result);
            System.out.println("Response body: ");
            System.out.println(put.getResponseBodyAsString());
        } finally {
            // Release current connection to the connection pool once you are
            // done
            put.releaseConnection();
        }

        getMember(1);

        // Sent HTTP POST request to add member
        System.out.println("\n");
        System.out.println("Sent HTTP POST request to add member");
        inputFile = client.getClass().getResource("/AddMember.xml").getFile();
        input = new File(inputFile);
        PostMethod post = new PostMethod(urlStem + "/members");
        post.addRequestHeader("Accept" , "text/xml");
        entity = new FileRequestEntity(input, "text/xml; charset=ISO-8859-1");
        post.setRequestEntity(entity);
        httpclient = new HttpClient();

        try {
            int result = httpclient.executeMethod(post);
            System.out.println("Response status code: " + result);
            System.out.println("Response body: ");
            System.out.println(post.getResponseBodyAsString());
        } finally {
            // Release current connection to the connection pool once you are
            // done
            post.releaseConnection();
        }

        getMember(2);

        System.out.println("\n");
        System.exit(0);
    } 
   /*    
    private static void getMember(int memberNo) throws Exception {
       // Sent HTTP GET request to query member info
       getMemberWebClientMethod(memberNo);
       
       System.out.println("Sent HTTP GET request to query member #" + memberNo);
       URL url = new URL(urlStem + "/members/" + memberNo);
       InputStream in = url.openStream();
       System.out.println(getStringFromInputStream(in)); 
   }  */

    private static void getMember(int memberNo) throws Exception {
       WebClient wc = WebClient.create(urlStem);
       wc.path("/members/" + memberNo);
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

