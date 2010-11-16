package service;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/main")
public interface PersonService {
    
	@GET
	@Produces({"application/xml", "application/json" })
	Collection<Person> getAll();
	
	@Path("{id}")
	Person getPersonSubresource(@PathParam("id") Long id);
    
	@POST
	@Path("{id}/children")
	@Consumes({"application/xml", "application/json" })
	Response addChild(@PathParam("id") Long id, Person child);
	
}
