/**
 * Copyright (C) 2010 Talend Inc. - www.talend.com
 */
package server;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.cxf.jaxrs.provider.JSONProvider;

import service.PersonExceptionMapper;
import service.PersonInfoStorage;
import service.PersonServiceImpl;
import service.SearchService;

@ApplicationPath("/personservice")
public class PersonApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>();
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> classes = new HashSet<Object>();

        PersonInfoStorage storage = new PersonInfoStorage();

        PersonServiceImpl personService = new PersonServiceImpl();
        personService.setStorage(storage);
        classes.add(personService);

        SearchService searchService = new SearchService();
        searchService.setStorage(storage);
        classes.add(searchService);

        // custom providers

        classes.add(new PersonExceptionMapper());

        JSONProvider provider = new JSONProvider();
        provider.setIgnoreNamespaces(true);
        classes.add(provider);

        return classes;
    }
}
