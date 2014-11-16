/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Helge Waastad <helge.waastad@datametrix.no>
 */
@XmlRootElement(name = "customers")
public class CustomerList implements Serializable {
    private static final long serialVersionUID = 1458054534508644635L;
    
    @JsonProperty("customers")
    private final List<Customer> customers;

    public CustomerList(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }   
}
