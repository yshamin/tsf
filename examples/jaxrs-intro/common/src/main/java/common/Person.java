package common;

import javax.xml.bind.annotation.XmlRootElement;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@XmlRootElement(name = "Person")
public class Person {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @GET
    public Person getState() {
    	return this;
    }
    
    @Path("name")
    public String getName() {
        return name;
    }

    @PUT
    @Consumes("text/plain")
    @Path("name")
    public void setName(String name) {
        this.name = name;
    }
}
