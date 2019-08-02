/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.controllers;

import com.bot.parser.olx.service.ClientService;
import com.bot.parser.olx.telegram.annotation.BotController;
import com.bot.parser.olx.telegram.annotation.BotRequestMapping;
import com.bot.parser.olx.telegram.annotation.BotRequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * StartController
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@BotController
@BotRequestMapping("/start")
public final class StartController {

    private final ClientService clientService;

    @Autowired
    public StartController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @BotRequestMapping(method = BotRequestMethod.MESSAGE)
    public SendMessage toStartMessage(final Update update) {
        final Long id = update.getMessage().getChatId();
        createNewClient(id);
        return createStartMessage(id);
    }

    @BotRequestMapping(method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toStartCallback(final Update update) {
        final Long id = update.getCallbackQuery().getMessage().getChatId();
        createNewClient(id);
        return createStartMessage(id);
    }

    private void createNewClient(final Long id) {
        this.clientService.createNew(id);
    }

    private SendMessage createStartMessage(final Long chatId) {
        return addInlineKeyboardButtons(
                new SendMessage(chatId, "Hello! I'm OLX parser bot.")
        );
    }

    private SendMessage addInlineKeyboardButtons(final SendMessage sendMessage) {
        final List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(
                new InlineKeyboardButton("Run")
                        .setCallbackData("/run")
        );
        keyboardRow1.add(
                new InlineKeyboardButton("Pause")
                        .setCallbackData("/pause")
        );

        final List<InlineKeyboardButton> keyboardRow2 = new ArrayList<>();
        keyboardRow2.add(
                new InlineKeyboardButton("Configuration")
                        .setCallbackData("/config")
        );

        final List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);

        return sendMessage.setReplyMarkup(
                new InlineKeyboardMarkup().setKeyboard(keyboardRows)
        );
    }
}
