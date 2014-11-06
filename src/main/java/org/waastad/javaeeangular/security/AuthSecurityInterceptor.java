/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.waastad.javaeeangular.ejb.AuthServiceBeanLocal;
import org.waastad.javaeeangular.model.AuthAccessElement;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@Provider
@Slf4j
public class AuthSecurityInterceptor implements ContainerRequestFilter {

    private static final Response ACCESS_UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).entity("Not authorized.").build();

    @Context
    private HttpServletRequest request;

    @Context
    private ResourceInfo resourceInfo;

    @EJB
    private AuthServiceBeanLocal authServiceBeanLocal;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authId = requestContext.getHeaderString(AuthAccessElement.PARAM_AUTH_ID);
        String authToken = requestContext.getHeaderString(AuthAccessElement.PARAM_AUTH_TOKEN);
        log.info("Filter: {} {}", authId, authToken);
        Method methodInvoked = resourceInfo.getResourceMethod();

        if (methodInvoked.isAnnotationPresent(RolesAllowed.class)) {
            log.info("Method is protected..");
            RolesAllowed rolesAllowedAnnotation = methodInvoked.getAnnotation(RolesAllowed.class);
            Set<String> rolesAllowed = new HashSet<>(Arrays.asList(rolesAllowedAnnotation.value()));
//            try {
//                authServiceBeanLocal = (AuthServiceBeanLocal) new InitialContext().lookup("java:global/AuthServiceBean!org.waastad.javaeeangular.ejb.AuthServiceBeanLocal");

            if (!authServiceBeanLocal.isAuthorized(authId, authToken, rolesAllowed)) {
                requestContext.abortWith(ACCESS_UNAUTHORIZED);
            }
//            } catch (NamingException e) {
//                log.error("Error: {}", ExceptionUtils.getRootCauseMessage(e));
//                requestContext.abortWith(ACCESS_UNAUTHORIZED);
//            }
        } else {
            log.info("Method is NOT protected..");
        }
    }

}
