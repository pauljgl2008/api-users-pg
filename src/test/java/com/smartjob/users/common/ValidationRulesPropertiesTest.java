package com.smartjob.users.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("ValidationRulesProperties Tests")
class ValidationRulesPropertiesTest {

    @Test
    void testBuilder() {
        String validationsPasswordRegex = "test-validationsPasswordRegex";

        ValidationRulesProperties testProperties = ValidationRulesProperties.builder()
                .validationsPasswordRegex(validationsPasswordRegex)
                .build();

        assertEquals(validationsPasswordRegex, testProperties.getValidationsPasswordRegex());
    }
}
