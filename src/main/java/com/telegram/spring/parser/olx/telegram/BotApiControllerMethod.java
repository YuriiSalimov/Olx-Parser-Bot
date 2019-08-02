/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.telegram;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collection;
import java.util.function.Function;

/**
 * MethodController
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public interface BotApiControllerMethod extends Function<Update, Collection<BotApiMethod<?>>> {
}
