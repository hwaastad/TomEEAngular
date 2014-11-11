/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.ejb;

import java.util.Set;
import javax.ejb.Local;
import org.waastad.javaeeangular.model.AuthAccessElement;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@Local
public interface AuthServiceBeanLocal {

    AuthAccessElement login(String username,String password);

    boolean isAuthorized(String authId, String authToken, Set<String> rolesAllowed);
}
