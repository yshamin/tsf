package client;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;

import service.Person;


public final class RESTClient {
    public static void main (String[] args) throws Exception {
    	
    	useWebClient();
        
    } 
    
    private static void useWebClient() throws Exception {
    	
    	WebClient wc = WebClient.create("http://localhost:8080/personservice");
    	
    	// get all persons
    	System.out.println("Getting the XML collection of all persons in a type-safe way...");
        wc.accept(MediaType.APPLICATION_XML);
        List<Person> persons = getPersons(wc);
        
        // get individual persons using JSON
        System.out.println("Getting individual persons using JSON...");
        wc.reset().accept(MediaType.APPLICATION_JSON);
        for (Person person : persons) {
        	// move forward
        	wc.path(person.getId());
        	
        	// read the stream
        	InputStream is = wc.get(InputStream.class);
            System.out.println(IOUtils.toString(is));
            
            // move one path segment back
            wc.back(false);
        }
        
        // Get Person with id 4 (still using JSON) :
        System.out.println("Getting info about Fred using JSON in a type-safe way...");
        wc.path("4");
        getPerson(wc);
    
        System.out.println("Getting info about Fred's mother");
        wc.path("mother");
        getPerson(wc);
        
        System.out.println("Getting info about Fred's father");
        wc.back(false).path("father");
        getPerson(wc);
        
        System.out.println("Getting info about Fred's partner");
        wc.back(false).path("partner");
        getPerson(wc);
        
        // Get info about Fred's ancestors, descendants and children
        wc.reset().accept(MediaType.APPLICATION_XML);
        wc.path("4");
        
        System.out.println("Getting info about Fred's ancestors");
        wc.path("ancestors");
        getPersons(wc);
                
        System.out.println("Getting info about Fred's descendants");
        wc.back(false).path("descendants");
        getPersons(wc);
        
        System.out.println("Getting info about Fred's children");
        wc.back(false).path("children");
        getPersons(wc);
        
        System.out.println("Fred and Catherine now have a child, adding a child info to PersonService");
        Person child = new Person("Harry", 1);
        Response response = wc.reset().path("4").path("children").post(child);
        
        // 201 status and the Location header pointing to 
        // a newly created (child) Person resource is expected
        if (response.getStatus() != 201) {
        	throw new RuntimeException("No child resource has been created");
        }
        
        String location = response.getMetadata().getFirst("Location").toString();
        WebClient childClient = WebClient.create(location);
        // get the information about the child, confirm it is Harry
        getPerson(childClient);
        
        System.out.println("Getting an up to date info about Fred's children");
        wc.back(false).path("children");
        getPersons(wc);
        
        System.out.println("Fred has become 40, updating his age");
        wc.back(false).path("age").type("text/plain").put(40);
        
        System.out.println("Getting up to date info about Fred");
        wc.back(false);
        getPerson(wc);
        
    }
    

    private static Person getPerson(WebClient wc) {
    	Person person = wc.get(Person.class);
        System.out.println("ID " + person.getId() + " : " + person.getName() + ", age : " + person.getAge());
        return person;
    }
    
    private static List<Person> getPersons(WebClient wc) {
    	List<Person> persons = new ArrayList<Person>(wc.getCollection(Person.class));
        for (Person person : persons) {
            System.out.println(
            	"ID " + person.getId() + " : " + person.getName() + ", age : " + person.getAge());
        }
        return persons;
    }
}

