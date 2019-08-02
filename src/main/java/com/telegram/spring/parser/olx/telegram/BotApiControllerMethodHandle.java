/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.telegram;

import com.telegram.spring.parser.olx.telegram.annotation.BotRequestMethod;
import com.telegram.spring.parser.olx.util.Container;
import com.telegram.spring.parser.olx.util.ObjectValidator;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * BotApiControllerMethodHandle
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public final class BotApiControllerMethodHandle implements Handle<Update, BotApiControllerMethod> {

    private final Container<String, BotApiControllerMethod> container;

    public BotApiControllerMethodHandle(final Container<String, BotApiControllerMethod> container) {
        this.container = container;
    }

    @Override
    public BotApiControllerMethod apply(final Update update) {
        BotApiControllerMethod method = this.container.get(getPath(update));
        if (ObjectValidator.isNull(method)) {
            method = new UnknownBotApiControllerMethod();
        }
        return method;
    }

    private String getPath(final Update update) {
        final String path;
        if (update.hasMessage() && update.getMessage().hasText()) {
            path = update.getMessage().getText().split(" ")[0].trim() + "-" + BotRequestMethod.MESSAGE;
        } else if (update.hasCallbackQuery()) {
            path = update.getCallbackQuery().getData().split("\\?")[0].trim() + "-" + BotRequestMethod.CALLBACK_QUERY;
        } else {
            path = "";
        }
        return path;
    }
}
