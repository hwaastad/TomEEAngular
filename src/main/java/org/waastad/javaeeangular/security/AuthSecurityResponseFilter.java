/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.security;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.waastad.javaeeangular.model.AuthAccessElement;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@Provider
@PreMatching
@Slf4j
public class AuthSecurityResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestCtx, ContainerResponseContext responseCtx) throws IOException {
        log.info("Filtering REST Response");
        responseCtx.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseCtx.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseCtx.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        responseCtx.getHeaders().add("Access-Control-Allow-Headers", AuthAccessElement.PARAM_AUTH_ID + ", " + AuthAccessElement.PARAM_AUTH_TOKEN);
    }

}
