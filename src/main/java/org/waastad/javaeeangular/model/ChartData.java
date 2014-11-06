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
public class ChartData implements Serializable {

    private static final long serialVersionUID = -3011523036502884948L;
    private final String year;
    private final String value;

    public ChartData(String year, String value) {
        this.year = year;
        this.value = value;
    }

    public String getYear() {
        return year;
    }

    public String getValue() {
        return value;
    }

}
