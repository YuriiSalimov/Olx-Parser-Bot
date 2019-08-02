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
 * CityController
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@BotController
@BotRequestMapping("/config/city")
public final class CityConfigController {

    private final ClientService clientService;

    @Autowired
    public CityConfigController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @BotRequestMapping(method = BotRequestMethod.MESSAGE)
    public SendMessage toConfigCityMessage(final Update update) {
        return createConfigCityResponse(update.getMessage().getChatId());
    }

    @BotRequestMapping(method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toConfigCityCallback(final Update update) {
        return createConfigCityResponse(update.getCallbackQuery().getMessage().getChatId());
    }

    @BotRequestMapping(value = "/set", method = BotRequestMethod.MESSAGE)
    public SendMessage setCityMessage(final Update update) {
        final Message message = update.getMessage();
        final Long id = message.getChatId();
        final String city = parseCity(message);
        updateCity(id, city);
        return createSetCityResponse(id, city);
    }

    @BotRequestMapping(value = "/set", method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage setCityCallback(final Update update) {
        final CallbackQuery callbackQuery = update.getCallbackQuery();
        final Long id = callbackQuery.getMessage().getChatId();
        final String city = parseCity(callbackQuery);
        updateCity(id, city);
        return createSetCityResponse(id, city);
    }

    private void updateCity(final Long clientId, final String city) {
        this.clientService.getById(clientId)
                .parseConfig()
                .addCity(city);
    }

    private SendMessage createConfigCityResponse(final Long chatId) {
        return addInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Please, choose new City from the list:"
                )
        );
    }

    private SendMessage createSetCityResponse(final Long chatId, final String city) {
        return addInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Done! Setted new city: " + city.toUpperCase()
                )
        );
    }

    private SendMessage addInlineKeyboardButtons(final SendMessage sendMessage) {
        final List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(
                new InlineKeyboardButton("Kiev")
                        .setCallbackData("/config/city/set?kiev")
        );
        keyboardRow1.add(
                new InlineKeyboardButton("Chernigov")
                        .setCallbackData("/config/city/set?chernigov")
        );

        final List<InlineKeyboardButton> keyboardRow2 = new ArrayList<>();
        keyboardRow2.add(
                new InlineKeyboardButton("Odessa")
                        .setCallbackData("/config/city/set?odessa")
        );
        keyboardRow2.add(
                new InlineKeyboardButton("Lvov")
                        .setCallbackData("/config/city/set?lvov")
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

    private String parseCity(final CallbackQuery callbackQuery) {
        return parseCity(callbackQuery.getData());
    }

    private String parseCity(final Message message) {
        return parseCity(message.getText());
    }

    private String parseCity(final String data) {
        return data.split("\\?")[1];
    }
}
