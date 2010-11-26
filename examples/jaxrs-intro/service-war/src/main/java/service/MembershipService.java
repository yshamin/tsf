package service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import common.Person;

@Path("/membershipservice/members")
public class MembershipService {
    int currentId = 0;
    Map<Integer, Person> members = new HashMap<Integer, Person>();

    public MembershipService() {
        Person p = new Person();
        p.setName("Bob");
        p.setId(++currentId);
        members.put(p.getId(), p);
    }

    /**
	 * Sub-resource locator (note the absence of HTTP Verb annotations such as GET).
	 * It locates a Person instance with a provided id and delegates to it to process
	 * the request. Note that a Person sub-resource may delegate to another sub-resource.
	 */
    @Path("/{id}")
	public Person getMemberSubresource(@PathParam("id") int id) {
    	System.out.println("getMemberSubresource called - id = " + id);
    	Person p = members.get(id);
    	System.out.println("p ID / Name = " + p.getId() + "; " + p.getName());
		return members.get(id);
	} 
    
    @POST
    public Response addMember(Person person) {
        System.out.println("----invoking addMember, Member name is: " + person.getName());
        person.setId(++currentId);
        members.put(person.getId(), person);
        return Response.ok(person).build();
    }

}

