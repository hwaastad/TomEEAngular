/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.ejb;

import java.util.Set;
import java.util.UUID;
import javax.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.waastad.javaeeangular.model.AuthAccessElement;
import org.waastad.javaeeangular.model.AuthLoginElement;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@Stateless
@Slf4j
public class AuthServiceBean implements AuthServiceBeanLocal {

    @Override
    public AuthAccessElement login(AuthLoginElement loginElement) {
        log.info("Log in user: {}", loginElement.getUsername());
        //Lookup User from db and create token etc if not null and save user with token.....
        return new AuthAccessElement(loginElement.getUsername(), UUID.randomUUID().toString(), "admin");

        // oterwise return null
    }

    @Override
    public boolean isAuthorized(String authId, String authToken, Set<String> rolesAllowed) {
        if (authId == null || authToken == null) {
            log.warn("Request is missing important headers....");
            return false;
        } else {
            log.info("Returing true....");
//        User user = userService.findByUsernameAndAuthToken(authId, authToken);
//        if (user != null) {
//            return rolesAllowed.contains(user.getAuthRole());
//        } else {
//            return false;
//        }
            return true;
        }
    }

}
