/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.telegram;

import com.bot.parser.olx.util.Container;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * BotApiMethodControllerContainer
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@Slf4j
public final class BotApiMethodControllerContainer implements Container<String, BotApiControllerMethod> {

    private final Map<String, BotApiControllerMethod> methodMap = new HashMap<>();

    @Override
    public void add(final String path, final BotApiControllerMethod method)
            throws BotApiMethodContainerException {
        if (this.methodMap.containsKey(path)) {
            throw new BotApiMethodContainerException("path " + path + " already added");
        }
        log.trace("Add telegram bot method for path: " + path);
        this.methodMap.put(path, method);
    }

    @Override
    public BotApiControllerMethod get(final String path) {
        return this.methodMap.get(path);
    }
}
