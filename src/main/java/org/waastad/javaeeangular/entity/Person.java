package org.waastad.javaeeangular.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;

@Entity
@NoSql(dataType = "person", dataFormat = DataFormatType.MAPPED)
@NamedQueries({
    @NamedQuery(name = Person.FIND_BY_ID, query = "SELECT t FROM Person t WHERE t.id=:id"),
    @NamedQuery(name = Person.FIND_ATTRIBUTE_BY_NAME, query = "SELECT t3.name FROM Person t JOIN t.attributeCollection t2 JOIN t2.optionCollection t3 where t3.name=:name"),
    @NamedQuery(name = "test", query = "SELECT COUNT(t2) FROM Person t JOIN t.attributeCollection t2")
})
public class Person implements Serializable {

    public static final String FIND_ATTRIBUTE_BY_NAME = "Person.findByAttName";
    public static final String FIND_BY_ID = "Person.findById";

    private static final long serialVersionUID = -7775273240236057754L;

    @Column(unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Id
    @GeneratedValue()
    private String id;
    @NotNull
    private String name;

    @ElementCollection
    @JsonProperty("attributes")
    private Collection<PersonAtribute> attributeCollection;

    public Person() {

    }

    public Person(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Collection<PersonAtribute> getAttributeCollection() {
        if (attributeCollection == null) {
            attributeCollection = new ArrayList<>();
        }
        return this.attributeCollection;
    }

    public void setAttributeCollection(List<PersonAtribute> attributeCollection) {
        this.attributeCollection = attributeCollection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}