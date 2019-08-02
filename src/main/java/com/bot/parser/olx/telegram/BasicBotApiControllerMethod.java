/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.telegram;

import com.bot.parser.olx.util.ObjectValidator;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

/**
 * BotApiMethodController
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@Slf4j
abstract class BasicBotApiControllerMethod implements BotApiControllerMethod {

    private final Object bean;
    private final Method method;
    private final Process updateProcess;

    BasicBotApiControllerMethod(final Object bean, final Method method) {
        this.bean = bean;
        this.method = method;
        this.updateProcess = typeCollectionReturnDetect() ? this::processCollection : this::processSingle;
    }

    @Override
    public Collection<BotApiMethod<?>> apply(final Update update) {
        if (successUpdatePredicate(update)) {
            try {
                return this.updateProcess.accept(update);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                log.error("bad invoke method", ex);
            }
        }
        return Collections.emptyList();
    }

    abstract boolean successUpdatePredicate(Update update);

    private boolean typeCollectionReturnDetect() {
        return Collection.class.equals(this.method.getReturnType());
    }

    private Collection<BotApiMethod<?>> processSingle(final Update update) throws InvocationTargetException, IllegalAccessException {
        final BotApiMethod botApiMethod = (BotApiMethod) this.method.invoke(this.bean, update);
        return ObjectValidator.isNotNull(botApiMethod) ? Collections.singletonList(botApiMethod) : Collections.emptyList();
    }

    private Collection<BotApiMethod<?>> processCollection(final Update update) throws InvocationTargetException, IllegalAccessException {
        final Collection<BotApiMethod<?>> botApiMethods = (Collection<BotApiMethod<?>>) this.method.invoke(this.bean, update);
        return ObjectValidator.isNotNull(botApiMethods) ? botApiMethods : Collections.emptyList();
    }

    private interface Process {

        Collection<BotApiMethod<?>> accept(Update update) throws InvocationTargetException, IllegalAccessException;
    }
}
