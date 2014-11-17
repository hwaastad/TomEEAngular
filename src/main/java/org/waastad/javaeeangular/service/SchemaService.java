/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.service;

import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.waastad.javaeeangular.entity.Option;
import org.waastad.javaeeangular.entity.Person;
import org.waastad.javaeeangular.entity.PersonAtribute;
import org.waastad.javaeeangular.respository.PersonRepository;

/**
 *
 * @author Helge Waastad <helge.waastad@datametrix.no>
 */
@Path("schema")
@Slf4j
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SchemaService {

    @Inject
    private PersonRepository personRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersons() {
        return Response.ok().entity(personRepository.findAll()).build();
    }

    @POST
    @ValidateOnExecution
    public Response postSchema(@NotNull(message = "Person may not be null") @Valid Person person) {
        return Response.ok().entity(personRepository.save(person)).build();
    }

    @GET
    @Path("{personId}")
    public Response getPersonPerson(@PathParam("personId") String personId) {
        Person findBy = personRepository.findById(personId);
        if (findBy == null) {
            return Response.ok().entity("Person Not Found").build();
        } else {
            personRepository.remove(findBy);
            return Response.ok().build();
        }

    }

    @DELETE
    @Path("{personId}")
    public Response deletePerson(@PathParam("personId") String personId) {
        Person findBy = personRepository.findById(personId);
        if (findBy == null) {
            return Response.ok().entity("Person Not Found").build();
        } else {
            personRepository.remove(findBy);
            return Response.ok().build();
        }

    }

    @GET
    @Path("{personId}/attributes")
    public Response getPersonAttributes(@PathParam("personId") String personId) {
        Person findBy = personRepository.findById(personId);
        if (findBy == null) {
            return Response.ok().entity("Person Not Found").build();
        }
        return Response.ok().entity(findBy.getAttributeCollection()).build();
    }

    @POST
    @Path("{personId}/attributes")
    public Response postPersonAttribute(@PathParam("personId") String personId, @NotNull @Valid PersonAtribute attribute) {
        Person findBy = personRepository.findById(personId);
        if (findBy == null) {
            return Response.ok().entity("Person Not Found").build();
        }
        findBy.getAttributeCollection().add(attribute);
        return Response.ok().entity(personRepository.save(findBy)).build();
    }

    @GET
    @Path("{personId}/attributes/{attributeId}")
    public Response getPersonAttribute(@PathParam("personId") String personId, @PathParam("attributeId") String attributeId) {
        Person findBy = personRepository.findById(personId);
        if (findBy == null) {
            return Response.ok().entity("Person Not Found").build();
        }
        return Response.ok().entity(findInCollection(findBy.getAttributeCollection(), attributeId)).build();
    }

    @DELETE
    @Path("{personId}/attributes/{attributeId}")
    public Response deletePersonAttribute(@PathParam("personId") String personId, @PathParam("attributeId") String attributeId) {
        Person findBy = personRepository.findById(personId);
        if (findBy == null) {
            return Response.ok().entity("Person Not Found").build();
        }
        PersonAtribute findInCollection = findInCollection(findBy.getAttributeCollection(), attributeId);
        if (findInCollection == null) {
            return Response.ok().entity("Attribute Not Found").build();
        }
        findBy.getAttributeCollection().remove(findInCollection);
        return Response.ok().entity(personRepository.save(findBy)).build();
    }

    @GET
    @Path("{personId}/attributes/{attributeId}/options")
    public Response getPersonAttributesOptions(@PathParam("personId") String personId, @PathParam("attributeId") String attributeId) {
        Person findBy = personRepository.findById(personId);
        if (findBy == null) {
            return Response.ok().entity("Person Not Found").build();
        }
        PersonAtribute findInCollection = findInCollection(findBy.getAttributeCollection(), attributeId);
        if (findInCollection == null) {
            return Response.ok().entity("Attribute Not Found").build();
        }
        return Response.ok().entity(findInCollection.getOptionCollection()).build();
    }

    @POST
    @Path("{personId}/attributes/{attributeId}/options")
    public Response postPersonAttributesOptions(@PathParam("personId") String personId, @PathParam("attributeId") String attributeId,
            @NotNull @Valid Option option) {
        Person findBy = personRepository.findById(personId);
        if (findBy == null) {
            return Response.ok().entity("Person Not Found").build();
        }
        PersonAtribute findInCollection = findInCollection(findBy.getAttributeCollection(), attributeId);
        if (findInCollection == null) {
            return Response.ok().entity("Attribute Not Found").build();
        }
        findInCollection.getOptionCollection().add(option);
        return Response.ok().entity(personRepository.save(findBy)).build();
    }

    private PersonAtribute findInCollection(Collection<PersonAtribute> list, final String name) {
        return CollectionUtils.find(list, new Predicate<PersonAtribute>() {

            @Override
            public boolean evaluate(PersonAtribute t) {
                return t.getName().equals(name);
            }

        });
    }

}
