/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.telegram;

import com.telegram.spring.parser.olx.util.ObjectValidator;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Method;

/**
 * BotApiCallbackQueryMethodController
 *
 * @author Yurii Salimov
 * @since 1.0
 */
final class BotApiCallbackQueryControllerMethod extends BasicBotApiControllerMethod {

    BotApiCallbackQueryControllerMethod(final Object bean, final Method method) {
        super(bean, method);
    }

    @Override
    boolean successUpdatePredicate(final Update update) {
        return ObjectValidator.isNotNull(update) &&
                update.hasCallbackQuery() &&
                (ObjectValidator.isNotNull(update.getCallbackQuery().getData()));
    }
}
