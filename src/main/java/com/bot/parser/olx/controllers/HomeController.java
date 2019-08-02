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
 * HomeController
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@BotController
@BotRequestMapping("/home")
public final class HomeController {

    @BotRequestMapping(method = BotRequestMethod.MESSAGE)
    public SendMessage toHomeMessage(final Update update) {
        return createHomeMessage(update.getMessage().getChatId());
    }

    @BotRequestMapping(method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toHomeCallback(final Update update) {
        return createHomeMessage(update.getCallbackQuery().getMessage().getChatId());
    }

    private SendMessage createHomeMessage(final Long chatId) {
        return addInlineKeyboardButtons(
                new SendMessage(chatId, "Home...")
        );
    }

    private SendMessage addInlineKeyboardButtons(final SendMessage sendMessage) {
        final List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(
                new InlineKeyboardButton("Run")
                        .setCallbackData("/run")
        );

        final List<InlineKeyboardButton> keyboardRow2 = new ArrayList<>();
        keyboardRow2.add(
                new InlineKeyboardButton("Pause")
                        .setCallbackData("/pause")
        );

        final List<InlineKeyboardButton> keyboardRow3 = new ArrayList<>();
        keyboardRow3.add(
                new InlineKeyboardButton("Configuration")
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
}
