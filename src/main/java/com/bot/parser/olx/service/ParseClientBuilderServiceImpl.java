/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.service;

import com.bot.parser.olx.parser.*;
import com.bot.parser.olx.util.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.Collection;

/**
 * ParseClientBuilderServiceImpl
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@Service
public class ParseClientBuilderServiceImpl implements ClientBuilderService {

    private final TelegramLongPollingBot telegramBot;
    private final UrlParams urlParams;
    private final DefaultUrlParams defaultUrlParams;
    private final ParseSelectors parseSelectors;

    @Autowired
    public ParseClientBuilderServiceImpl(
            final TelegramLongPollingBot telegramBot,
            final UrlParams urlParams,
            final DefaultUrlParams defaultUrlParams,
            final ParseSelectors parseSelectors
    ) {
        this.telegramBot = telegramBot;
        this.urlParams = urlParams;
        this.defaultUrlParams = defaultUrlParams;
        this.parseSelectors = parseSelectors;
    }

    @Override
    public ParseClient<?> apply(final Long id) throws IllegalArgumentException {
        if (ObjectValidator.isNull(id)) {
            throw new IllegalArgumentException(
                    "Input id must be not null!"
            );
        }
        return createNewUser(id);
    }

    private ParseClient<?> createNewUser(final Long id) {
        return new TelegramParseClient(
                id,
                createParserBuilder(),
                this.telegramBot
        );
    }

    private ParserBuilder<Collection<Offer>> createParserBuilder() {
        return new OffersParserBuilder(
                this.urlParams,
                this.defaultUrlParams,
                this.parseSelectors
        );
    }
}
