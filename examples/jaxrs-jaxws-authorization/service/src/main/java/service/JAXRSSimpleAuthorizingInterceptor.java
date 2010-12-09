/**
 * Copyright (C) 2010 Talend Inc. - www.talend.com
 */
package service;

import java.lang.reflect.Method;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.security.AccessDeniedException;
import org.apache.cxf.interceptor.security.SimpleAuthorizingInterceptor;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.Message;
import org.apache.cxf.security.SecurityContext;

/**
 * Custom CXF In Interceptor. Currently, CXF SimpleAuthorizingInterceptor can
 * only determine the service Method to be invoked in case of JAX-WS
 * invocations. This interceptor extends and 'helps' it to find the Method in
 * case of JAX-RS invocations. Note that this interceptor will be removed in the
 * future versions of this demo due to CXF SimpleAuthorizingInterceptor being
 * able to deal with both JAX-WS and JAX-RS invocations at the latest CXF trunk.
 */
public class JAXRSSimpleAuthorizingInterceptor extends SimpleAuthorizingInterceptor {

    @Override
    public void handleMessage(Message message) throws Fault {
        SecurityContext sc = message.get(SecurityContext.class);
        if (sc == null) {
            return;
        }

        // Unfortunately : this method is currently has a private scope
        // in the superclass
        Method method = getTargetMethod(message);

        if (authorize(sc, method)) {
            return;
        }

        throw new AccessDeniedException("Unauthorized");
    }

    private Method getTargetMethod(Message message) {
        // OperationResourceInfo represents the information about the
        // current JAX-RS resource method about to be invoked
        OperationResourceInfo ori = message.getExchange().get(OperationResourceInfo.class);
        return ori.getMethodToInvoke();
    }

}
