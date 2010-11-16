package service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import common.Person;

@Path("/membershipservice/")
public class MembershipService {
    long currentId = 0;
    Map<Long, Person> members = new HashMap<Long, Person>();

    public MembershipService() {
        Person p = new Person();
        p.setName("Bob");
        p.setId(++currentId);
        members.put(p.getId(), p);
    }

    @GET
    @Path("/members/{id}/")
    public Person getMember(@PathParam("id") String id) {
        System.out.println("----invoking getMember, Person id is: " + id);
        long idNumber = Long.parseLong(id);
        Person p = members.get(idNumber);
        return p;
    }

    @PUT
    @Path("/members/")
    public Response updateMember(Person person) {
        System.out.println("----invoking updateMember, Member name is: " + person.getName());
        Person p = members.get(person.getId());
        Response r;
        if (p != null) {
            members.put(person.getId(), person);
            r = Response.ok().build();
        } else {
            r = Response.notModified().build();
        }
        return r;
    }

    @POST
    @Path("/members/")
    public Response addMember(Person person) {
        System.out.println("----invoking addMember, Member name is: " + person.getName());
        person.setId(++currentId);

        members.put(person.getId(), person);

        return Response.ok(person).build();
    }

    @DELETE
    @Path("/members/{id}/")
    public Response deleteMember(@PathParam("id") String id) {
        System.out.println("----invoking deleteMember, Member id is: " + id);
        long idNumber = Long.parseLong(id);
        Person p = members.get(idNumber);

        Response r;
        if (p != null) {
            members.remove(idNumber);
            r = Response.ok().build();
        } else {
            r = Response.notModified().build();
        }

        return r;
    }
}

