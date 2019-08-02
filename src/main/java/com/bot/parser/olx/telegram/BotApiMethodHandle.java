/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.telegram;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collection;

/**
 * BotApiMethodHandle
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public final class BotApiMethodHandle implements Handle<Update, Collection<BotApiMethod<?>>> {

    private final Handle<Update, BotApiControllerMethod> methodHandle;

    public BotApiMethodHandle(final Handle<Update, BotApiControllerMethod> methodHandle) {
        this.methodHandle = methodHandle;
    }

    @Override
    public Collection<BotApiMethod<?>> apply(final Update update) {
        return handleBotApiControllerMethod(update).apply(update);
    }

    private BotApiControllerMethod handleBotApiControllerMethod(final Update update) {
        return this.methodHandle.apply(update);
    }
}
