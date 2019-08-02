/*
 * Created by Yurii Salimov, 02.08.2019
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
 * DelayTimeConfigController
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@BotController
@BotRequestMapping("/config/delay-time")
public final class DelayTimeConfigController {

    private final ClientService clientService;

    @Autowired
    public DelayTimeConfigController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @BotRequestMapping(method = BotRequestMethod.MESSAGE)
    public SendMessage toConfigDelayTimeMessage(final Update update) {
        return createConfigDelayTimeResponse(update.getMessage().getChatId());
    }

    @BotRequestMapping(method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toConfigDelayTimeCallback(final Update update) {
        return createConfigDelayTimeResponse(update.getCallbackQuery().getMessage().getChatId());
    }

    @BotRequestMapping(value = "/set", method = BotRequestMethod.MESSAGE)
    public SendMessage setDelayTimeMessage(final Update update) {
        final Message message = update.getMessage();
        final Long id = message.getChatId();
        final long delayTime = parseDelayTime(message);
        updateDelayTime(id, delayTime);
        return createSetDelayTimeResponse(id, delayTime);
    }

    @BotRequestMapping(value = "/set", method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage setDelayTimeCallback(final Update update) {
        final CallbackQuery callbackQuery = update.getCallbackQuery();
        final Long id = callbackQuery.getMessage().getChatId();
        final long delayTime = parseDelayTime(callbackQuery);
        updateDelayTime(id, delayTime);
        return createSetDelayTimeResponse(id, delayTime);
    }

    private void updateDelayTime(final Long clientId, final long delayTime) {
        this.clientService.getById(clientId)
                .setDelayTime(delayTime);
    }

    private SendMessage createConfigDelayTimeResponse(final Long chatId) {
        return addInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Choose a delay time between repeated requests:"
                )
        );
    }

    private SendMessage createSetDelayTimeResponse(final Long chatId, final long delayTime) {
        return addInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Done! Setted new delay time: " + delayTime + " m"
                )
        );
    }

    private SendMessage addInlineKeyboardButtons(final SendMessage sendMessage) {
        final List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(
                new InlineKeyboardButton("30 m")
                        .setCallbackData("/config/delay-time/set?30")
        );
        keyboardRow1.add(
                new InlineKeyboardButton("1 hour")
                        .setCallbackData("/config/delay-time/set?60")
        );

        final List<InlineKeyboardButton> keyboardRow2 = new ArrayList<>();
        keyboardRow2.add(
                new InlineKeyboardButton("2 hour")
                        .setCallbackData("/config/delay-time/set?120")
        );
        keyboardRow2.add(
                new InlineKeyboardButton("3 hour")
                        .setCallbackData("/config/delay-time/set?180")
        );

        final List<InlineKeyboardButton> keyboardRow3 = new ArrayList<>();
        keyboardRow3.add(
                new InlineKeyboardButton("6 hour")
                        .setCallbackData("/config/delay-time/set?360")
        );
        keyboardRow3.add(
                new InlineKeyboardButton("12 hour")
                        .setCallbackData("/config/delay-time/set?720")
        );

        final List<InlineKeyboardButton> keyboardRow4 = new ArrayList<>();
        keyboardRow4.add(
                new InlineKeyboardButton("Back")
                        .setCallbackData("/config")
        );

        final List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
        keyboardRows.add(keyboardRow4);

        return sendMessage.setReplyMarkup(
                new InlineKeyboardMarkup().setKeyboard(keyboardRows)
        );
    }

    private long parseDelayTime(final CallbackQuery callbackQuery) {
        return parseDelayTime(callbackQuery.getData());
    }

    private long parseDelayTime(final Message message) {
        return parseDelayTime(message.getText());
    }

    private long parseDelayTime(final String data) {
        return Long.parseLong(data.split("\\?")[1]);
    }
}
