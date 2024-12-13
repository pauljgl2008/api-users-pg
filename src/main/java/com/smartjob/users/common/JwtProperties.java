package com.smartjob.users.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Builder
@Getter
public final class JwtProperties {

    private String issuer;

    private String secret;

    private Long expiredTime;

}
