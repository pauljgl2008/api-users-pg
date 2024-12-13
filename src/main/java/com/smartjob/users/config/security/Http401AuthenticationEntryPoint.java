package com.smartjob.users.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@AllArgsConstructor
public class Http401AuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Log logger = LogFactory.getLog(Http401AuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException {
        logger.debug("Pre-authenticated entry point called. Rejecting access");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println("{ \"mensaje_error_entry_point\": \"" + arg2.getMessage() + "\" }");
    }

}