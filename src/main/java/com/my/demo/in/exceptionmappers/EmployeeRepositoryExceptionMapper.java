package com.my.demo.in.exceptionmappers;

import com.my.demo.in.dto.ErrorResponseData;
import com.my.demo.out.exceptions.EmployeeRepositoryException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception mapper for {@link EmployeeRepositoryException}
 */
@Provider
public class EmployeeRepositoryExceptionMapper implements ExceptionMapper<EmployeeRepositoryException> {

    /**
     * Returns the response when the exception occurs
     *
     * @param e the exception
     * @return the response with the exception message wrapped in a class
     */
    @Override
    public Response toResponse(EmployeeRepositoryException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponseData(e.getMessage()))
                .build();
    }
}
