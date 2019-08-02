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
 * PromotedConfigController
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@BotController
@BotRequestMapping("/config/promoted")
public final class PromotedConfigController {

    private final ClientService clientService;

    @Autowired
    public PromotedConfigController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @BotRequestMapping(method = BotRequestMethod.MESSAGE)
    public SendMessage toConfigPromotedMessage(final Update update) {
        return createConfigPromotedResponse(update.getMessage().getChatId());
    }

    @BotRequestMapping(method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toConfigPromotedCallback(final Update update) {
        return createConfigPromotedResponse(update.getCallbackQuery().getMessage().getChatId());
    }

    @BotRequestMapping(value = "/set", method = BotRequestMethod.MESSAGE)
    public SendMessage setPromotedMessage(final Update update) {
        final Message message = update.getMessage();
        final Long id = message.getChatId();
        final boolean promoted = parsePromoted(message);
        updatePromoted(id, promoted);
        return createSetPromotedResponse(id, promoted);
    }

    @BotRequestMapping(value = "/set", method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage setPromotedCallback(final Update update) {
        final CallbackQuery callbackQuery = update.getCallbackQuery();
        final Long id = callbackQuery.getMessage().getChatId();
        final boolean promoted = parsePromoted(callbackQuery);
        updatePromoted(id, promoted);
        return createSetPromotedResponse(id, promoted);
    }

    private void updatePromoted(final Long clientId, final boolean promoted) {
        this.clientService.getById(clientId)
                .parseConfig()
                .addWithPromotedOffers(promoted);
    }

    private SendMessage createConfigPromotedResponse(final Long chatId) {
        return addInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Show promoted offers?"
                )
        );
    }

    private SendMessage createSetPromotedResponse(
            final Long chatId,
            final boolean promoted
    ) {
        final String message = "Done! " +
                (promoted ? "with" : "without") +
                " promoted offers...";
        return addInlineKeyboardButtons(
                new SendMessage(chatId, message)
        );
    }

    private SendMessage addInlineKeyboardButtons(final SendMessage sendMessage) {
        final List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(
                new InlineKeyboardButton("Yes")
                        .setCallbackData("/config/promoted/set?true")
        );
        keyboardRow1.add(
                new InlineKeyboardButton("No")
                        .setCallbackData("/config/promoted/set?false")
        );

        final List<InlineKeyboardButton> keyboardRow2 = new ArrayList<>();
        keyboardRow2.add(
                new InlineKeyboardButton("Back")
                        .setCallbackData("/config")
        );

        final List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);

        return sendMessage.setReplyMarkup(
                new InlineKeyboardMarkup().setKeyboard(keyboardRows)
        );
    }

    private boolean parsePromoted(final CallbackQuery callbackQuery) {
        return parsePromoted(callbackQuery.getData());
    }

    private boolean parsePromoted(final Message message) {
        return parsePromoted(message.getText());
    }

    private boolean parsePromoted(final String data) {
        return Boolean.parseBoolean(data.split("\\?")[1]);
    }
}
