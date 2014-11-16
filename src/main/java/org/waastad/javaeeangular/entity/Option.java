package org.waastad.javaeeangular.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;

@Embeddable
@NoSql(dataFormat = DataFormatType.MAPPED)
public class Option implements Serializable {

    private static final long serialVersionUID = -2864668708158957612L;

    @Column(unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Basic
    private String name;

    @Column(unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Basic
    private String value;

    public Option() {

    }

    public Option(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
