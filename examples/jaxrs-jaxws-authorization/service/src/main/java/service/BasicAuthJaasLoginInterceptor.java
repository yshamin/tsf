/**
 * Copyright (C) 2010 Talend Inc. - www.talend.com
 */
package service;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.security.AccessDeniedException;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.security.SecurityContext;

public class BasicAuthJaasLoginInterceptor extends AbstractPhaseInterceptor<Message> {

    private static final String CONTEXT_NAME = "karaf";
    
    public BasicAuthJaasLoginInterceptor() {
        super(Phase.UNMARSHAL);
    }
    
    @Override
    public void handleMessage(Message message) throws Fault {
        
        AuthorizationPolicy policy = (AuthorizationPolicy)message.get(AuthorizationPolicy.class);
        if (policy == null) {
            throw new SecurityException("No information about the current user is available");
        }
        
        try {
            NamePasswordCallbackHandler handler 
                = new NamePasswordCallbackHandler(policy.getUserName(), policy.getPassword());  
            LoginContext ctx = new LoginContext(CONTEXT_NAME, handler);  
            ctx.login();
            
            Subject subject = ctx.getSubject();
            
            message.put(SecurityContext.class, 
                        new SecurityContextImpl(subject));
        } catch (LoginException ex) {
             throw new AccessDeniedException("Unauthorized : " + ex.getMessage());
        }
    }

    private class NamePasswordCallbackHandler implements CallbackHandler {  
         private String username;  
         private String password;  
         
         public NamePasswordCallbackHandler(String username, String password) {  
             this.username = username;  
             this.password = password;  
         }  
         
         public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {  
             for (int i = 0; i < callbacks.length; i++) {  
                 Callback callback = callbacks[i];  
                 if (callback instanceof NameCallback) {  
                     ((NameCallback) callback).setName(username);  
                 } else if (callback instanceof PasswordCallback) {  
                     PasswordCallback pwCallback = (PasswordCallback) callback;  
                     pwCallback.setPassword(password.toCharArray());  
                 } else {  
                     throw new UnsupportedCallbackException(callbacks[i], "Callback type not supported");  
                 }  
             }  
        }      
    }
    
    private static class SecurityContextImpl implements SecurityContext {
        private static final String ROLE_PREFIX = "ROLE_";
        private Principal p;
        private Set<String> roles; 
        
        public SecurityContextImpl(Subject subject) {
            this.p = findPrincipal(subject);
            this.roles = findRoles(subject);
        }
        
        public Principal getUserPrincipal() {
            return p;
        }
    
        public boolean isUserInRole(String role) {
            return roles.contains(role);
        }
        
        private static Principal findPrincipal(Subject subject) {
            for (Principal p : subject.getPrincipals()) {
                if (!p.getName().startsWith(ROLE_PREFIX)) {
                    return p;
                }
            }
            return null;
        }
        
        private static Set<String> findRoles(Subject subject) {
            Set<String> set = new HashSet<String>();
            for (Principal p : subject.getPrincipals()) {
                if (p.getName().startsWith(ROLE_PREFIX)) {
                    set.add(p.getName());
                }
            }
            return set;
        }
    }
}
