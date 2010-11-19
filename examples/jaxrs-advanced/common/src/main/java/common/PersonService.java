package common;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * 
 * This interface describes a JAX-RS root resource.
 * All the JAXRS annotations will be inherited by classes implementing it.
 */
@Path("/main")
public interface PersonService {
    
	/**
	 * Returns an explicit collection of all known persons 
	 * in either XML or JSON formats in response to HTTP GET requests
	 */
	@GET
	@Produces({"application/xml", "application/json" })
	Collection<Person> getAll();
	
	/**
	 * Sub-resource locator (note the absence of HTTP Verb annotations such as GET).
	 * It locates a Person instance with a provided id and delegates to it to process
	 * the request. Note that a Person sub-resource may delegate to another sub-resource.
	 */
	@Path("{id}")
	Person getPersonSubresource(@PathParam("id") Long id);
    
	/**
	 * Adds a child to the existing Person. It is expected to return an HTTP 201 status 
	 * and Location header pointing to a newly created child resource. Note that
	 * JAX-RS Response can have a status, headers, and response entity returned.
	 */
	@POST
	@Path("{id}/children")
	@Consumes({"application/xml", "application/json" })
	Response addChild(@PathParam("id") Long id, Person child);
	
}
