package com.my.demo.in.exceptionmappers;

import com.my.demo.domain.exceptions.CyclicStructureException;
import com.my.demo.in.dto.ErrorResponseData;
import com.my.demo.in.exceptionmappers.CyclicStructureExceptionMapper;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

class CyclicStructureExceptionMapperTest {

    private final CyclicStructureExceptionMapper exceptionMapper;

    CyclicStructureExceptionMapperTest() {
        this.exceptionMapper = new CyclicStructureExceptionMapper();
    }

    @Test
    void toResponse() {
        CyclicStructureException expectedException = new CyclicStructureException("Expected exception");
        Response response = exceptionMapper.toResponse(expectedException);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
        assertThat(((ErrorResponseData)response.getEntity()).getMessage()).hasToString("Expected exception");
    }
}