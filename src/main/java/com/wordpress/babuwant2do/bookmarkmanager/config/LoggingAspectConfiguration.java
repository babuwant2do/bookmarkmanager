package com.wordpress.babuwant2do.bookmarkmanager.config;



import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import com.wordpress.babuwant2do.bookmarkmanager.aop.LoggingAspect;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
