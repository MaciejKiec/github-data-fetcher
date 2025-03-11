package org.kiec.clients;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.LinkedHashMap;
import java.util.Map;

@Provider
public class UserNotFoundExceptionProvider implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException e) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("status", e.getResponse().getStatus());
        if (e.getResponse().getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            errorResponse.put("message", "Cannot find user with given name");
        } else {
            errorResponse.put("message", e.getResponse().getStatusInfo().getReasonPhrase());
        }

        return Response
                .status(e.getResponse().getStatus())
                .entity(errorResponse)
                .build();
    }
}
