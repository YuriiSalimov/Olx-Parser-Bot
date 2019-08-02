/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.telegram;

import com.telegram.spring.parser.olx.util.ObjectValidator;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * UnknownBotApiControllerMethod
 *
 * @author Yurii Salimov
 * @since 1.0
 */
final class UnknownBotApiControllerMethod implements BotApiControllerMethod {

    @Override
    public Collection<BotApiMethod<?>> apply(final Update update) {
        return Collections.singletonList(
                addInlineKeyboardButtons(
                        new SendMessage(
                                getChatId(update),
                                "Unknown command..."
                        )
                )
        );
    }

    private Long getChatId(final Update update) {
        return ObjectValidator.isNotNull(update.getMessage()) ?
                update.getMessage().getChatId() :
                update.getCallbackQuery().getMessage().getChatId();
    }

    private SendMessage addInlineKeyboardButtons(final SendMessage sendMessage) {
        final List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(
                new InlineKeyboardButton()
                        .setText("Home")
                        .setCallbackData("/home")
        );

        final List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow1);

        return sendMessage.setReplyMarkup(
                new InlineKeyboardMarkup().setKeyboard(keyboardRows)
        );
    }
}
