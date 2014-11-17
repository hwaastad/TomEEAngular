/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.provider;

import com.fasterxml.jackson.core.JsonParseException;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 *
 * @author Helge Waastad <helge.waastad@datametrix.no>
 */
@Provider
@Slf4j
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {

    @Context
    private Request headers;

    @Override
    public Response toResponse(JsonParseException e) {
        final Response.ResponseBuilder builder = Response.status(Response.Status.NOT_ACCEPTABLE);
        final List<Variant> variants = Variant.mediaTypes(MediaType.APPLICATION_XML_TYPE,
                MediaType.APPLICATION_JSON_TYPE).add().build();

        final Variant variant = headers.selectVariant(variants);
        if (variant != null) {
            builder.type(variant.getMediaType());
        }
        ValidationError error = new ValidationError(null, "Incorrect JSON format",null);
        builder.entity(
                new GenericEntity<>(
                        error,
                        new GenericType<ValidationError>() {
                        }.getType()
                )
        );
//        builder.entity(
//                new GenericEntity<>(
//                        getEntity(e.getConstraintViolations()),
//                        new GenericType<List<ValidationError>>() {
//                        }.getType()
//                )
//        );

        return builder.build();
    }

}
