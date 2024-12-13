package com.smartjob.users.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("JwtProperties Tests")
class JwtPropertiesTest {

    @Test
    void testBuilder() {
        String testIssuer = "www.smartjob.com";
        String testSecret = "test-secret";
        Long testExpiredTime = 3600L;

        JwtProperties testProperties = JwtProperties.builder()
                .issuer(testIssuer)
                .secret(testSecret)
                .expiredTime(testExpiredTime)
                .build();

        assertEquals(testIssuer, testProperties.getIssuer());
        assertEquals(testSecret, testProperties.getSecret());
        assertEquals(testExpiredTime, testProperties.getExpiredTime());
    }

}
