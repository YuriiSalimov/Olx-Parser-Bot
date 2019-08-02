/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collection;

/**
 * TelegramBot
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@Slf4j
@Component
public final class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    private final Handle<Update, Collection<BotApiMethod<?>>> updateHandle;

    @Autowired
    public TelegramBot(final Handle<Update, Collection<BotApiMethod<?>>> botApiMethodUpdateHandle) {
        this.updateHandle = botApiMethodUpdateHandle;
    }

    @Override
    public void onUpdateReceived(final Update update) {
        this.updateHandle.apply(update).forEach(
                botApiMethod -> {
                    try {
                        execute(botApiMethod);
                    } catch (TelegramApiException ex) {
                        log.error(ex.getMessage(), ex);
                    }
                }
        );
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }
}
