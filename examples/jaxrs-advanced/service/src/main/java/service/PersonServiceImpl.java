package service;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import common.Person;
import common.PersonService;

/**
 * JAX-RS PersonService root resource
 * 
 */
public class PersonServiceImpl implements PersonService {

   private PersonInfoStorage storage;

   /**
    * Thread-safe JAX-RS UriInfo proxy providing the information about the
    * current request URI, etc
    */
   @Context
   private UriInfo uriInfo;

   public PersonServiceImpl() {
   }

   public void setStorage(PersonInfoStorage storage) {
      this.storage = storage;
   }

   @Override
   public Person getPersonSubresource(Long id) {
      return storage.getPerson(id);
   }

   @Override
   public Collection<Person> getPersons(Integer start, Integer size) {
      return storage.getPersons(start, size);
   }

   @Override
   public Response addChild(Long parentId, Person child) {
      Person parent = storage.getPerson(parentId);
      if (parent == null) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }

      parent.addChild(child);

      Long childId = storage.addPerson(child);

      UriBuilder locationBuilder = uriInfo.getBaseUriBuilder();
      locationBuilder.path(PersonService.class);
      URI childLocation = locationBuilder.path("{id}").build(childId);

      return Response.status(Response.Status.CREATED).location(childLocation)
            .build();
   }

}
