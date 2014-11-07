/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.service;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.waastad.javaeeangular.controller.ApplicationBean;
import org.waastad.javaeeangular.model.Customer;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@Path("customer")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class CustomerService {

    @Inject
    private ApplicationBean applicationBean;

    @GET
    @RolesAllowed("admin")
    public Response getCustomers() {
        log.info("Someone is getting all customers...");
//        throw new WebApplicationException("Somthing is terribly wrong here...");
        return Response.ok(applicationBean.getCustomers()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCustomer(Customer customer) {
        applicationBean.addToList(customer);
        return Response.ok().build();
    }

    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") Integer id) {
        return Response.ok(applicationBean.getCustomer(id)).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") Integer id, Customer customer) {
        return Response.ok(applicationBean.modifyCustomer(id, customer)).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response deleteCustomer(@PathParam("id") Integer id) {
        applicationBean.removeFromList(id);
        return Response.ok().build();
    }

}
