package service;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public class PersonServiceImpl implements PersonService {

	private static AtomicLong ID = new AtomicLong();
	private Map<Long, Person> persons = 
		Collections.synchronizedMap(new LinkedHashMap<Long, Person>());
	
	@Context
	private UriInfo uriInfo;
	
	public PersonServiceImpl() {
		init();
	}
	
	@Override
	public Person getPersonSubresource(Long id) {
		return persons.get(id);
	}

	@Override
	public Collection<Person> getAll() {
		return persons.values();
	}
	
	@Override
	public Response addChild(Long id, Person child) {
		Person person = persons.get(id);
		if (person == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		person.addChild(child);
		
		long childId = ID.incrementAndGet();
		child.setId(childId);
		persons.put(childId, child);
		
		UriBuilder locationBuilder = uriInfo.getBaseUriBuilder();
		URI childLocation = locationBuilder.path("{id}").build(childId);
		
		return Response.status(Response.Status.CREATED).location(childLocation).build();
	}
	
	private void init() { 
		
		Person mother = new Person("Lorrain", 50);
		mother.setId(ID.incrementAndGet());
		persons.put(mother.getId(), mother);
		
		Person father = new Person("John", 55);
		father.setId(ID.incrementAndGet());
		persons.put(father.getId(), father);
		
		Person partner = new Person("Catherine", 28);
		partner.setId(ID.incrementAndGet());
		persons.put(partner.getId(), partner);
		
	    Person p = new Person("Fred", 30, mother, father, partner);
	    p.setId(ID.incrementAndGet());
		persons.put(p.getId(), p);
	}
}
