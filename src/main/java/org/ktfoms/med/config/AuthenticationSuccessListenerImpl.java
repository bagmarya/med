package org.ktfoms.med.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListenerImpl implements ApplicationListener<AuthenticationSuccessEvent> {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessListenerImpl.class);

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent appEvent) {
        AuthenticationSuccessEvent event = appEvent;
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        if (userDetails != null) {
            logger.info("В систему вошел пользователь: " + userDetails.getUsername());
        }
    }
}
