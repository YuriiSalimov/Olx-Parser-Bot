/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.controllers;

import com.telegram.spring.parser.olx.service.ClientService;
import com.telegram.spring.parser.olx.telegram.annotation.BotController;
import com.telegram.spring.parser.olx.telegram.annotation.BotRequestMapping;
import com.telegram.spring.parser.olx.telegram.annotation.BotRequestMethod;
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
 * DistanceController
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@BotController
@BotRequestMapping("/config/distance")
public final class DistanceConfigController {

    private final ClientService clientService;

    @Autowired
    public DistanceConfigController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @BotRequestMapping(method = BotRequestMethod.MESSAGE)
    public SendMessage toConfigDistanceMessage(final Update update) {
        return createConfigDistanceResponse(update.getMessage().getChatId());
    }

    @BotRequestMapping(method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toConfigDistanceCallback(final Update update) {
        return createConfigDistanceResponse(update.getCallbackQuery().getMessage().getChatId());
    }

    @BotRequestMapping(value = "/set", method = BotRequestMethod.MESSAGE)
    public SendMessage setDistanceMessage(final Update update) {
        final Message message = update.getMessage();
        final Long id = message.getChatId();
        final int distance = parseDistance(message);
        updateDistance(id, distance);
        return createSetDistanceResponse(id, distance);
    }

    @BotRequestMapping(value = "/set", method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage setDistanceCallback(final Update update) {
        final CallbackQuery callbackQuery = update.getCallbackQuery();
        final Long id = callbackQuery.getMessage().getChatId();
        final int distance = parseDistance(callbackQuery);
        updateDistance(id, distance);
        return createSetDistanceResponse(id, distance);
    }

    private void updateDistance(final Long clientId, final int distance) {
        this.clientService.getById(clientId)
                .parseConfig()
                .addDistance(distance);
    }

    private SendMessage createConfigDistanceResponse(final Long chatId) {
        return addInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Choose distance to a city:"
                )
        );
    }

    private SendMessage createSetDistanceResponse(final Long chatId, final int distance) {
        return addInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Done! Setted new distance: " + distance + " km"
                )
        );
    }

    private SendMessage addInlineKeyboardButtons(final SendMessage sendMessage) {
        final List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(
                new InlineKeyboardButton("0 km")
                        .setCallbackData("/config/distance/set?0")
        );
        keyboardRow1.add(
                new InlineKeyboardButton("5 km")
                        .setCallbackData("/config/distance/set?5")
        );

        final List<InlineKeyboardButton> keyboardRow2 = new ArrayList<>();
        keyboardRow2.add(
                new InlineKeyboardButton("10 km")
                        .setCallbackData("/config/distance/set?10")
        );
        keyboardRow2.add(
                new InlineKeyboardButton("20 km")
                        .setCallbackData("/config/distance/set?20")
        );

        final List<InlineKeyboardButton> keyboardRow3 = new ArrayList<>();
        keyboardRow3.add(
                new InlineKeyboardButton("Back")
                        .setCallbackData("/config")
        );

        final List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);

        return sendMessage.setReplyMarkup(
                new InlineKeyboardMarkup().setKeyboard(keyboardRows)
        );
    }

    private int parseDistance(final CallbackQuery callbackQuery) {
        return parseDistance(callbackQuery.getData());
    }

    private int parseDistance(final Message message) {
        return parseDistance(message.getText());
    }

    private int parseDistance(final String data) {
        return Integer.parseInt(data.split("\\?")[1]);
    }
}
