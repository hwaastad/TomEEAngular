/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@XmlRootElement
public class AuthLoginElement implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String username;
    @NotNull
    private String password;

    public AuthLoginElement() {
    }

    public AuthLoginElement(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AuthLoginElement{" + "username=" + username + ", password=" + password + '}';
    }

}
