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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
@Slf4j
public class AuthService {

    @Inject
    private AuthServiceBeanLocal authServiceBeanLocal;

    @POST
    @PermitAll
    public AuthAccessElement login(@Context HttpServletRequest request, @Valid AuthLoginElement loginElement) {
        log.info("Logging in user: {}", loginElement.toString());
//        throw new WebApplicationException("Somthing is terribly wrong here...");

        AuthAccessElement login = authServiceBeanLocal.login(loginElement.getUsername(), loginElement.getPassword());
        if (login != null) {
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_ID, login.getAuthId());
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_TOKEN, login.getAuthToken());
        }
        return login;
    }

    @POST
    @Path("form")
    @PermitAll
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public AuthAccessElement loginForm(@Context HttpServletRequest request,
            @FormParam("username") @NotNull String username, @FormParam("password") @NotNull String password) {
        log.info("Logging in user: {}", username);
//        throw new WebApplicationException("Somthing is terribly wrong here...");

        AuthAccessElement login = authServiceBeanLocal.login(username,password);
        if (login != null) {
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_ID, login.getAuthId());
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_TOKEN, login.getAuthToken());
        }
        return login;
    }

    @DELETE
    @PermitAll
    public void logout(@Context HttpServletRequest requestContext) {
        String authToken = requestContext.getHeader(AuthAccessElement.PARAM_AUTH_TOKEN);
        log.info("I will log {} out...", authToken);
    }
}
