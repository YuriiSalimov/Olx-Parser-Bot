/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.telegram;

/**
 * BotApiMethodContainerException
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public class BotApiMethodContainerException extends RuntimeException {

    public BotApiMethodContainerException() {
        super();
    }

    public BotApiMethodContainerException(final String message) {
        super(message);
    }

    public BotApiMethodContainerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BotApiMethodContainerException(final Throwable cause) {
        super(cause);
    }
}
