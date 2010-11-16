package service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Persons", namespace = "http://org.persons")
public class PersonCollection {
	private List<Person> list = new ArrayList<Person>();
	
	public void addPerson(Person person) {
		getList().add(person);
	}

	public void setList(List<Person> list) {
		this.list = list;
	}

	public List<Person> getList() {
		return list;
	}
}
