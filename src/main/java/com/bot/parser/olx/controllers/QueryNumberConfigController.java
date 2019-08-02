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
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * QueryNumberConfigController
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@BotController
@BotRequestMapping("/config/query-number")
public final class QueryNumberConfigController {

    private final ClientService clientService;

    @Autowired
    public QueryNumberConfigController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @BotRequestMapping(method = BotRequestMethod.MESSAGE)
    public SendMessage toConfigQueryNumberMessage(final Update update) {
        return createConfigQueryNumberResponse(update.getMessage().getChatId());
    }

    @BotRequestMapping(method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toConfigQueryNumberCallback(final Update update) {
        return createConfigQueryNumberResponse(update.getCallbackQuery().getMessage().getChatId());
    }

    @BotRequestMapping(value = "/set", method = BotRequestMethod.MESSAGE)
    public SendMessage setQueryNumberMessage(final Update update) {
        final Message message = update.getMessage();
        final Long id = message.getChatId();
        final int queryNumber = parseQueryNumber(message);
        updateQueryNumber(id, queryNumber);
        return createSetQueryNumberResponse(id, queryNumber);
    }

    @BotRequestMapping(value = "/set", method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage setQueryNumberCallback(final Update update) {
        final CallbackQuery callbackQuery = update.getCallbackQuery();
        final Long id = callbackQuery.getMessage().getChatId();
        final int queryNumber = parseQueryNumber(callbackQuery);
        updateQueryNumber(id, queryNumber);
        return createSetQueryNumberResponse(id, queryNumber);
    }

    private void updateQueryNumber(final Long clientId, final int queryNumber) {
        this.clientService.getById(clientId)
                .setNumberOfResult(queryNumber);
    }

    private SendMessage createConfigQueryNumberResponse(final Long chatId) {
        return addInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Choose the number of parsing results."
                )
        );
    }

    private SendMessage createSetQueryNumberResponse(final Long chatId, final int queryNumber) {
        return addInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Done! Setted new number: " + queryNumber
                )
        );
    }

    private SendMessage addInlineKeyboardButtons(final SendMessage sendMessage) {
        final List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(
                new InlineKeyboardButton("5")
                        .setCallbackData("/config/query-number/set?5")
        );
        keyboardRow1.add(
                new InlineKeyboardButton("10")
                        .setCallbackData("/config/query-number/set?10")
        );

        final List<InlineKeyboardButton> keyboardRow2 = new ArrayList<>();
        keyboardRow2.add(
                new InlineKeyboardButton("15")
                        .setCallbackData("/config/query-number/set?15")
        );
        keyboardRow2.add(
                new InlineKeyboardButton("20")
                        .setCallbackData("/config/query-number/set?20")
        );

        final List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);

        return sendMessage.setReplyMarkup(
                new InlineKeyboardMarkup().setKeyboard(keyboardRows)
        );
    }

    private int parseQueryNumber(final CallbackQuery callbackQuery) {
        return parseQueryNumber(callbackQuery.getData());
    }

    private int parseQueryNumber(final Message message) {
        return parseQueryNumber(message.getText());
    }

    private int parseQueryNumber(final String data) {
        return Integer.parseInt(data.split("\\?")[1]);
    }
}
