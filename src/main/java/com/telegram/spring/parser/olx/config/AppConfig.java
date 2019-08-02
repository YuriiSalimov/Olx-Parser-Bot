/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.config;

import com.telegram.spring.parser.olx.telegram.*;
import com.telegram.spring.parser.olx.util.Container;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collection;

/**
 * ApppConfig
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@Configuration
public class AppConfig {

    @Bean
    @Scope("singleton")
    public Container<String, BotApiControllerMethod> botApiControllerMethodContainer() {
        return new BotApiMethodControllerContainer();
    }

    @Bean
    public Handle<Update, BotApiControllerMethod> botApiControllerMethodHandle(
            final Container<String, BotApiControllerMethod> botApiControllerMethodContainer
    ) {
        return new BotApiControllerMethodHandle(botApiControllerMethodContainer);
    }

    @Bean
    public Handle<Update, Collection<BotApiMethod<?>>> botApiMethodUpdateHandle(
            final Handle<Update, BotApiControllerMethod> botApiControllerMethodHandle
    ) {
        return new BotApiMethodHandle(botApiControllerMethodHandle);
    }
}
