package com.httq.config;

import com.httq.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.WebAttributes;
import javax.servlet.http.HttpSession;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Autowired
    @Qualifier("userDetailsService")
    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Authentication auth = super.authenticate(authentication);

            //if reach here, means login success, else exception will be thrown
            //reset the user_attempts
            userService.resetFailAttempts(authentication.getName());

            return auth;

        } catch (BadCredentialsException e) {
            //invalid login, update to user_attempts
            try {
                userService.updateFailAttempts(authentication.getName());
            }catch (LockedException lockedException){
                session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, lockedException);
                throw lockedException;
            }
            session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, e);
            throw e;
        }catch (AuthenticationException authenticationException){
            session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, authenticationException);
            throw authenticationException;
        }
    }
}
