/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.controllers;

import com.bot.parser.olx.telegram.annotation.BotController;
import com.bot.parser.olx.telegram.annotation.BotRequestMapping;
import com.bot.parser.olx.telegram.annotation.BotRequestMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * ConfigController
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@BotController
@BotRequestMapping("/config")
public final class ConfigController {

    @BotRequestMapping(method = BotRequestMethod.MESSAGE)
    public SendMessage toConfigMessage(final Update update) {
        return createConfigResponse(update.getMessage().getChatId());
    }

    @BotRequestMapping(method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toConfigCallback(final Update update) {
        return createConfigResponse(update.getCallbackQuery().getMessage().getChatId());
    }

    private SendMessage createConfigResponse(final Long chatId) {
        return addInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Parsing Configuration\n" +
                                "Select the item you want to change:")
        );
    }

    private SendMessage addInlineKeyboardButtons(final SendMessage sendMessage) {
        final List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(
                new InlineKeyboardButton("City")
                        .setCallbackData("/config/city")
        );
        keyboardRow1.add(
                new InlineKeyboardButton("Distance")
                        .setCallbackData("/config/distance")
        );

        final List<InlineKeyboardButton> keyboardRow2 = new ArrayList<>();
        keyboardRow2.add(
                new InlineKeyboardButton()
                        .setText("Price")
                        .setCallbackData("/config/price")
        );
        keyboardRow2.add(
                new InlineKeyboardButton("Rooms")
                        .setCallbackData("/config/rooms-number")
        );

        final List<InlineKeyboardButton> keyboardRow3 = new ArrayList<>();
        keyboardRow3.add(
                new InlineKeyboardButton("Query Number")
                        .setCallbackData("/config/query-number")
        );
        keyboardRow3.add(
                new InlineKeyboardButton("Delay Time")
                        .setCallbackData("/config/delay-time")
        );

        final List<InlineKeyboardButton> keyboardRow4 = new ArrayList<>();
        keyboardRow4.add(
                new InlineKeyboardButton("Promoted")
                        .setCallbackData("/config/promoted")
        );
        keyboardRow4.add(
                new InlineKeyboardButton("Sorting")
                        .setCallbackData("/config/sort")
        );

        final List<InlineKeyboardButton> keyboardRow5 = new ArrayList<>();
        keyboardRow5.add(
                new InlineKeyboardButton("Home")
                        .setCallbackData("/home")
        );

        final List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
        keyboardRows.add(keyboardRow4);
        keyboardRows.add(keyboardRow5);

        return sendMessage.setReplyMarkup(
                new InlineKeyboardMarkup().setKeyboard(keyboardRows)
        );
    }
}
