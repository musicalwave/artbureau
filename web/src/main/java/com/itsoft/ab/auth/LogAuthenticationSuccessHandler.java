package com.itsoft.ab.auth;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.itsoft.ab.logger.LoggerConstants.AUTH_LOGGER;

/**
 * Created with IntelliJ IDEA.
 * User: Павел
 * Date: 15.01.14
 * Time: 18:36
 * To change this template use File | Settings | File Templates.
 */
public class LogAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final static Logger LOGGER = Logger.getLogger(AUTH_LOGGER);

    public void onAuthenticationSuccess(HttpServletRequest request,
                                       HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String login = ((UserDetails) authentication.getPrincipal()).getUsername();
        LOGGER.info("User with login \"" + login + "\" has logged in.");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
