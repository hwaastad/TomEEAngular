/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.inject.Provider;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import javax.validation.ValidationException;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;
import javax.ws.rs.ext.ExceptionMapper;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Helge Waastad <helge.waastad@datametrix.no>
 */
@javax.ws.rs.ext.Provider
@Slf4j
public class ViolationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Context
    private Request request;

    @Override
    public Response toResponse(ValidationException e) {
        if (e instanceof ConstraintViolationException) {
            log.info("Constraint violation: {}", e);
            final ConstraintViolationException cve = (ConstraintViolationException) e;
            final Response.ResponseBuilder builder = Response.status(getStatus(cve));
            final List<Variant> variants = Variant.mediaTypes(MediaType.APPLICATION_XML_TYPE,
                    MediaType.APPLICATION_JSON_TYPE).add().build();
            final Variant variant = request.selectVariant(variants);

            if (variant != null) {
                builder.type(variant.getMediaType());
            }
            builder.entity(
                    new GenericEntity<>(
                            getEntity(cve.getConstraintViolations()),
                            new GenericType<List<ValidationError>>() {
                            }.getType()
                    )
            );

            return builder.build();

        } else {
            log.warn("Unexpected Bean Validation problem.", e);

            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    private List<ValidationError> getEntity(final Set<ConstraintViolation<?>> violations) {
        final List<ValidationError> errors = new ArrayList<>();

        for (final ConstraintViolation<?> violation : violations) {
            log.info("Validating value: {},message: {},path:{}", violation.getInvalidValue(), violation.getMessage(), getPath(violation));
            errors.add(new ValidationError(getInvalidValue(violation.getInvalidValue()), violation.getMessage(), getPath(violation)));
        }

        return errors;
    }

    private String getInvalidValue(final Object invalidValue) {
        if (invalidValue == null) {
            return null;
        }

        if (invalidValue.getClass().isArray()) {
            return Arrays.toString((Object[]) invalidValue);
        }

        return invalidValue.toString();
    }

    private Response.Status getStatus(final ConstraintViolationException exception) {
        return getResponseStatus(exception.getConstraintViolations());
    }

    private Response.Status getResponseStatus(final Set<ConstraintViolation<?>> constraintViolations) {
        final Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();

        if (iterator.hasNext()) {
            return getResponseStatus(iterator.next());
        } else {
            return Response.Status.BAD_REQUEST;
        }
    }

    private Response.Status getResponseStatus(final ConstraintViolation<?> constraintViolation) {
        for (final Path.Node node : constraintViolation.getPropertyPath()) {
            final ElementKind kind = node.getKind();

            if (ElementKind.RETURN_VALUE.equals(kind)) {
                return Response.Status.INTERNAL_SERVER_ERROR;
            }
        }

        return Response.Status.BAD_REQUEST;
    }

    private String getPath(final ConstraintViolation<?> violation) {
        final String leafBeanName = violation.getLeafBean().getClass().getSimpleName();
        final String leafBeanCleanName = (leafBeanName.contains("$")) ? leafBeanName.substring(0,
                leafBeanName.indexOf("$")) : leafBeanName;
        final String propertyPath = violation.getPropertyPath().toString();

        return leafBeanCleanName + (!"".equals(propertyPath) ? '.' + propertyPath : "");
    }

}
