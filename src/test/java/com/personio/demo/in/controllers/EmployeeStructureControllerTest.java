package com.personio.demo.in.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personio.demo.domain.usecases.StructureUseCase;
import com.personio.demo.domain.usecases.StructureVerificationUsecase;
import com.personio.demo.exceptions.CyclicStructureException;
import com.personio.demo.exceptions.MultipleRootSupervisorException;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(EmployeeStructureController.class)
class EmployeeStructureControllerTest {

    @InjectMock
    StructureUseCase useCase;

//    private EmployeeStructureController controller;

    @BeforeEach
    public void setUp() {
//        this.controller = new EmployeeStructureController(useCase);
    }

    @Test
    void testOrganize() throws JsonProcessingException, MultipleRootSupervisorException, CyclicStructureException {
        Map<String, String> input = new HashMap<>();
        input.put("Pete", "Nick");
        input.put("Barbara", "Nick");
        input.put("Nick", "Sophie");
        input.put("Sophie", "Jonas");

        when(useCase.parseHierarchy(anyMap())).thenReturn(Json.createObjectBuilder().add("supervisor", Json.createObjectBuilder().build()).build());

        ValidatableResponse response = given().contentType(ContentType.JSON)
                .when().body(asJsonString(input)).post()
                .then();

        response.statusCode(200);
        response.body("supervisor.size()", CoreMatchers.is(0));
    }

    /**
     * Function used to map an object into a JsonString
     *
     * @param obj the object to be mapped
     * @return a json string representing the object
     * @throws JsonProcessingException thrown when could not parse the object
     */
    private String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}