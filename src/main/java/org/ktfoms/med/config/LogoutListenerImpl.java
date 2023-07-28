package org.ktfoms.med.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class LogoutListenerImpl implements ApplicationListener<LogoutSuccessEvent> {
    private static final Logger logger = LoggerFactory.getLogger(LogoutListenerImpl.class);
    @Override
    public void onApplicationEvent(LogoutSuccessEvent event) {
        String name = event.getAuthentication().getName();
        logger.info("Систему покинул пользователь: " + name);
    }
}