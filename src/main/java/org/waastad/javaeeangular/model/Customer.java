/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@XmlRootElement
public class Customer implements Serializable {

    private static final long serialVersionUID = -6203322119784387479L;
    private Integer id;
    private String name;

    public Customer() {
    }

    public Customer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + '}';
    }

}
