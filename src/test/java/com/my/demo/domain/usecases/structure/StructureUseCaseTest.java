package com.my.demo.domain.usecases.structure;

import com.my.demo.domain.exceptions.CyclicStructureException;
import com.my.demo.domain.exceptions.MultipleRootSupervisorException;
import com.my.demo.domain.commons.StructureMapToNodeUtil;
import com.my.demo.domain.usecases.employee.EmployeeUseCase;
import com.my.demo.domain.usecases.structure.StructureUseCase;
import com.my.demo.domain.usecases.structure.StructureVerificationUsecase;
import com.my.demo.out.exceptions.EmployeeRepositoryException;
import com.my.demo.out.exceptions.SupervisorRepositoryException;
import io.vertx.mutiny.pgclient.PgPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.JsonObject;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StructureUseCaseTest {

    private StructureUseCase useCase;

    @BeforeEach
    void init() {
        StructureVerificationUsecase mockStructureVerificationUsecase = mock(StructureVerificationUsecase.class);
        StructureMapToNodeUtil mockUtil = mock(StructureMapToNodeUtil.class);
        EmployeeUseCase mockEmployeeUseCase = mock(EmployeeUseCase.class);
        when(mockUtil.mapToNode(any())).thenCallRealMethod();
        this.useCase = new StructureUseCase(mockStructureVerificationUsecase, mockEmployeeUseCase, mockUtil);
    }

    @Test
    void testParseHierarchy() throws MultipleRootSupervisorException, CyclicStructureException, EmployeeRepositoryException, SupervisorRepositoryException {
        Map<String, String> input = new HashMap<>();
        input.put("Pete", "Nick");
        input.put("Barbara", "Nick");
        input.put("Nick", "Sophie");
        input.put("Sophie", "Jonas");

        JsonObject parentJson = useCase.parseHierarchy(mock(PgPool.class), input);

        JsonObject jonasJson = parentJson.get("Jonas").asJsonObject();
        assertThat(jonasJson.size()).isEqualTo(1);

        JsonObject sophieJson = jonasJson.get("Sophie").asJsonObject();
        assertThat(sophieJson.size()).isEqualTo(1);

        JsonObject nickJson = sophieJson.get("Nick").asJsonObject();
        assertThat(nickJson.size()).isEqualTo(2);

        JsonObject peteJson = nickJson.get("Pete").asJsonObject();
        assertThat(peteJson.size()).isZero();

        JsonObject barbaraJson = nickJson.get("Barbara").asJsonObject();
        assertThat(barbaraJson.size()).isZero();
    }
}