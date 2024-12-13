package com.smartjob.users.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
@Builder
@Getter
public class ValidationRulesProperties {

    @Value("${validations.password.regex}")
    private String validationsPasswordRegex;

}

