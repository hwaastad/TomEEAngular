/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.service;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.waastad.javaeeangular.controller.ApplicationBean;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@Singleton
@Path("chartdata")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChartService {

    @Inject
    private ApplicationBean applicationBean;

    @GET
    public Response getChartData() {
        return Response.ok(applicationBean.getChartData()).build();
    }
    
    @GET
    @Path("pie")
    public Response getPieChartData() {
        return Response.ok(applicationBean.getPieChartData()).build();
    }
}
