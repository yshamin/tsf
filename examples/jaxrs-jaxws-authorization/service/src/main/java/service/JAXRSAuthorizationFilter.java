/**
 * Copyright (C) 2010 Talend Inc. - www.talend.com
 */
package service;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.cxf.interceptor.security.AccessDeniedException;
import org.apache.cxf.interceptor.security.SimpleAuthorizingInterceptor;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;

/**
 * CXF JAX-RS filter showing how to convert a CXF-specific AccessDeniedException
 * into HTTP 403 code. Note that this filter will become redundant in the future
 * versions of this demo due to CXF JAX-RS runtime providing a helper security
 * filter for enforcing authorization rules at the latest trunk.
 */
public class JAXRSAuthorizationFilter implements RequestHandler {

    private SimpleAuthorizingInterceptor interceptor = new JAXRSSimpleAuthorizingInterceptor();

    public void setMethodRolesMap(Map<String, String> rolesMap) {
        interceptor.setMethodRolesMap(rolesMap);
    }

    public Response handleRequest(Message m, ClassResourceInfo resourceClass) {
        try {
            interceptor.handleMessage(m);
            // successful authorization - no need to block the request
            return null;
        } catch (AccessDeniedException ex) {
            // returning a Response blocks the request

            // convert the exception into HTTP 403
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

}
