/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.repository;

import java.util.List;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.SingleResultType;
import org.waastad.javaeeangular.entity.Option;
import org.waastad.javaeeangular.entity.Person;

/**
 *
 * @author Helge Waastad <helge.waastad@datametrix.no>
 */
@Repository
public abstract class PersonRepository extends AbstractEntityRepository<Person, String> {

    @Query(named = Person.FIND_ATTRIBUTE_BY_NAME)
    public abstract List<Option> findByName(@QueryParam("name") String name);

    @Query(named = Person.FIND_BY_ID,singleResult = SingleResultType.OPTIONAL)
    public abstract Person findById(@QueryParam("id") String id);

}
