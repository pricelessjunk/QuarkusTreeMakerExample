package com.my.demo.domain.usecases.structure;

import com.my.demo.domain.entities.Node;
import com.my.demo.domain.exceptions.CyclicStructureException;
import com.my.demo.domain.exceptions.MultipleRootSupervisorException;
import com.my.demo.domain.commons.StructureMapToNodeUtil;
import com.my.demo.domain.usecases.structure.StructureVerificationUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

class StructureVerificationUsecaseTest {
    StructureVerificationUsecase verificationUsecase;
    StructureMapToNodeUtil util;

    @BeforeEach
    void init() {
        verificationUsecase = new StructureVerificationUsecase();
        util = new StructureMapToNodeUtil();
    }

    @Test
    void testVerifyMultipleRoot() {
        Map<String, String> inputStructure = new HashMap<>();
        inputStructure.put("Sophie", "Jonas");
        inputStructure.put("Nick", "Sophie");
        inputStructure.put("Barbara", "Pete");

        Map<String, Node> tracker = util.mapToNode(inputStructure);

        assertThrows(MultipleRootSupervisorException.class, () -> verificationUsecase.verifyMultipleRoot(tracker), "An exception is expected to be thrown");
    }

    @Test
    void testVerifyCyclicReference() {
        Map<String, String> inputStructure = new HashMap<>();
        inputStructure.put("Sophie", "Jonas");
        inputStructure.put("Pete", "Nick");
        inputStructure.put("Barbara", "Nick");
        inputStructure.put("Nick", "Sophie");
        inputStructure.put("Jonas", "Pete");

        Map<String, Node> tracker = util.mapToNode(inputStructure);

        assertThrows(CyclicStructureException.class, () -> verificationUsecase.verifyCyclicReference(tracker), "An exception is expected to be thrown");
    }
}