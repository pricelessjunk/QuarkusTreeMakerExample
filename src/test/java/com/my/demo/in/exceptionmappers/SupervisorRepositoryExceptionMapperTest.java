package com.my.demo.in.exceptionmappers;

import com.my.demo.in.dto.ErrorResponseData;
import com.my.demo.in.exceptionmappers.SupervisorRepositoryExceptionMapper;
import com.my.demo.out.exceptions.SupervisorRepositoryException;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

class SupervisorRepositoryExceptionMapperTest {
    private final SupervisorRepositoryExceptionMapper exceptionMapper;

    SupervisorRepositoryExceptionMapperTest() {
        this.exceptionMapper = new SupervisorRepositoryExceptionMapper();
    }

    @Test
    void toResponse() {
        SupervisorRepositoryException expectedException = new SupervisorRepositoryException("Expected exception");
        Response response = exceptionMapper.toResponse(expectedException);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
        assertThat(((ErrorResponseData)response.getEntity()).getMessage()).hasToString("Expected exception");
    }
}