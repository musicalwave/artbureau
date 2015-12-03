package com.itsoft.ab.auth;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.itsoft.ab.logger.LoggerConstants.AUTH_LOGGER;

/**
 * Created with IntelliJ IDEA.
 * User: Павел
 * Date: 15.01.14
 * Time: 18:46
 * To change this template use File | Settings | File Templates.
 */
public class LogAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final static Logger LOGGER = Logger.getLogger(AUTH_LOGGER);

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String login = request.getParameter("j_username");
        String pass = request.getParameter("j_password");
        LOGGER.info("Failed to log in with login \"" + login + "\".");
        response.sendRedirect(request.getContextPath() +"/login?error=1");
    }
}
