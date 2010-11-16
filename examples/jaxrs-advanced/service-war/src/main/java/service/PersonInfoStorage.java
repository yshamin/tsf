package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class PersonInfoStorage {
	private static AtomicLong ID = new AtomicLong();
	private Map<Long, Person> persons = 
		Collections.synchronizedMap(new LinkedHashMap<Long, Person>());
	
	public PersonInfoStorage() {
		init();
	}
	
	public Person getPerson(Long id) {
		return persons.get(id);
	}
	
	public Long addPerson(Person person) {
		long id = ID.incrementAndGet();
		person.setId(id);
		persons.put(id, person);
		return id;
	}
	
	public Collection<Person> getAll() {
		return new ArrayList<Person>(persons.values());
	}
	
    private void init() { 
		
		Person mother = new Person("Lorraine", 50);
		addPerson(mother);
		
		Person father = new Person("John", 55);
		addPerson(father);
		
		Person partner = new Person("Catherine", 28);
		addPerson(partner);
		
	    Person p = new Person("Fred", 30, mother, father, partner);
	    addPerson(p);
	}
}
