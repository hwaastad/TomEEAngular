/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.provider;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Helge Waastad <helge.waastad@datametrix.no>
 */
@XmlRootElement
public class ValidationError {

    private String invalidValue;
    private String message;
    private String path;

    public ValidationError() {
    }

    public ValidationError(final String invalidValue, final String message,final String path) {
        this.invalidValue = invalidValue;
        this.message = message;
        this.path = path;
    }

    public String getInvalidValue() {
        return invalidValue;
    }

    public void setInvalidValue(String invalidValue) {
        this.invalidValue = invalidValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
