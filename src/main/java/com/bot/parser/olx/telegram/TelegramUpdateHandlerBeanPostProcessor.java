/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.telegram;

import com.bot.parser.olx.telegram.annotation.BotController;
import com.bot.parser.olx.telegram.annotation.BotRequestMapping;
import com.bot.parser.olx.telegram.annotation.BotRequestMethod;
import com.bot.parser.olx.util.Container;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * TelegramUpdateHandlerBeanPostProcessor
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@Slf4j
@Component
public final class TelegramUpdateHandlerBeanPostProcessor implements BeanPostProcessor {

    private final Container<String, BotApiControllerMethod> container;

    @Autowired
    public TelegramUpdateHandlerBeanPostProcessor(
            final Container<String, BotApiControllerMethod> botApiControllerMethodContainer
    ) {
        this.container = botApiControllerMethodContainer;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(BotController.class)) {
            storeBotRequestMethods(bean);
        }
        return bean;
    }

    private void storeBotRequestMethods(final Object bean) {
        Arrays.stream(bean.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(BotRequestMapping.class))
                .forEach((Method method) -> storeBotRequestMethod(bean, method));
    }

    private void storeBotRequestMethod(final Object bean, final Method method) {
        final BotApiControllerMethod botApiControllerMethod = createBotApiControllerMethod(bean, method);
        generatePaths(bean.getClass(), method)
                .forEach(path -> this.container.add(path, botApiControllerMethod));
    }

    private BotApiControllerMethod createBotApiControllerMethod(final Object bean, final Method method) {
        final BotRequestMapping methodMapping = method.getAnnotation(BotRequestMapping.class);
        return BotRequestMethod.MESSAGE.equals(methodMapping.method()) ?
                new BotApiMessageControllerMethod(bean, method) :
                new BotApiCallbackQueryControllerMethod(bean, method);
    }

    private Collection<String> generatePaths(final Class<?> controller, final Method method) {
        final BotRequestMapping methodMapping = method.getAnnotation(BotRequestMapping.class);
        Collection<String> paths = Arrays.stream(methodMapping.value())
                .map(path -> path + "-" + methodMapping.method())
                .collect(Collectors.toList());

        if (controller.isAnnotationPresent(BotRequestMapping.class)) {
            final BotRequestMapping controllerMapping = controller.getAnnotation(BotRequestMapping.class);
            final Collection<String> newPaths = new ArrayList<>();
            for (String controllerPath : controllerMapping.value()) {
                for (String methodPath : paths) {
                    newPaths.add(controllerPath + methodPath);
                }
            }
            paths = newPaths;
        }
        return paths;
    }
}
