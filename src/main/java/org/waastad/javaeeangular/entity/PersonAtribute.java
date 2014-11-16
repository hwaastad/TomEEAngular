package org.waastad.javaeeangular.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotNull;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;

@Embeddable
@NoSql(dataFormat = DataFormatType.MAPPED)
public class PersonAtribute implements Serializable {

    private static final long serialVersionUID = -2779430876131223068L;

    @Column(unique = true)
    @NotNull
    private String name;

    @Column(unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Basic
    private Boolean required;

    @ElementCollection
    @JsonProperty("options")
    private Collection<Option> optionCollection;

    @Column(unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Basic
    private String regexp;

    public PersonAtribute() {

    }

    public PersonAtribute(Boolean required, String regexp) {
        this.required = required;
        this.regexp = regexp;
    }

    public Boolean isRequired() {
        return this.required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Collection<Option> getOptionCollection() {
        if (optionCollection == null) {
            optionCollection = new ArrayList<>();
        }
        return this.optionCollection;
    }

    public void setOptionCollection(List<Option> optionCollection) {
        this.optionCollection = optionCollection;
    }

    public String getRegexp() {
        return this.regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
