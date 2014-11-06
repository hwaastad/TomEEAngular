/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.service;

import javax.annotation.security.PermitAll;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.waastad.javaeeangular.ejb.AuthServiceBeanLocal;
import org.waastad.javaeeangular.model.AuthAccessElement;
import org.waastad.javaeeangular.model.AuthLoginElement;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@Path("auth")
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
@Slf4j
public class AuthService {

    @Inject
    private AuthServiceBeanLocal authServiceBeanLocal;

    @POST
    @PermitAll
    public AuthAccessElement login(@Context HttpServletRequest request, AuthLoginElement loginElement) {
        log.info("Logging in user: {}", loginElement.toString());
//        throw new WebApplicationException("Somthing is terribly wrong here...");

        AuthAccessElement login = authServiceBeanLocal.login(loginElement);
        if (login != null) {
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_ID, login.getAuthId());
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_TOKEN, login.getAuthToken());
        }
        return login;
    }
}
