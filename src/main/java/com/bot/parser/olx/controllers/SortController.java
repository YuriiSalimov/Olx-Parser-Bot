/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.controllers;

import com.bot.parser.olx.parser.ParserBuilder;
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
 * SortController
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@BotController
@BotRequestMapping("/config/sort")
public final class SortController {

    private final ClientService clientService;

    @Autowired
    public SortController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @BotRequestMapping(method = BotRequestMethod.MESSAGE)
    public SendMessage toConfigSortingMessage(final Update update) {
        return createConfigSortingResponse(update.getMessage().getChatId());
    }

    @BotRequestMapping(method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toConfigSortingCallback(final Update update) {
        return createConfigSortingResponse(update.getCallbackQuery().getMessage().getChatId());
    }

    @BotRequestMapping(value = "/set", method = BotRequestMethod.MESSAGE)
    public SendMessage toSetSortingMessage(final Update update) {
        final Message message = update.getMessage();
        final Long id = message.getChatId();
        final String sortType = parseSortType(message);
        updateSort(id, sortType);
        return createSetSortingResponse(id, sortType);
    }

    @BotRequestMapping(value = "/set", method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toSetSortingCallback(final Update update) {
        final CallbackQuery callbackQuery = update.getCallbackQuery();
        final Long id = callbackQuery.getMessage().getChatId();
        final String sortType = parseSortType(callbackQuery);
        updateSort(id, sortType);
        return createSetSortingResponse(id, sortType);
    }

    private void updateSort(final Long clientId, final String sortType) {
        final ParserBuilder<?> parserBuilder = this.clientService.getById(clientId).parseConfig();
        if ("latest".equals(sortType)) {
            parserBuilder.sortByDate();
        } else if ("cheap".equals(sortType)) {
            parserBuilder.sortByCheap();
        } else if ("expensive".equals(sortType)) {
            parserBuilder.sortByExpensive();
        }
    }

    private SendMessage createConfigSortingResponse(final Long chatId) {
        return addInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Please, select a new sort type:"
                )
        );
    }

    private SendMessage createSetSortingResponse(final Long chatId, final String sortType) {
        final String sortName;
        if ("latest".equals(sortType)) {
            sortName = "Latest first";
        } else if ("cheap".equals(sortType)) {
            sortName = "From cheap to expensive";
        } else if ("expensive".equals(sortType)) {
            sortName = "From expensive to cheap";
        } else {
            sortName = "?";
        }
        return addInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Done! Setted new sort type: " + sortName
                )
        );
    }

    private SendMessage addInlineKeyboardButtons(final SendMessage sendMessage) {
        final List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(
                new InlineKeyboardButton("Latest first")
                        .setCallbackData("/config/sort/set?latest")
        );

        final List<InlineKeyboardButton> keyboardRow2 = new ArrayList<>();
        keyboardRow2.add(
                new InlineKeyboardButton("From Cheap to Expensive")
                        .setCallbackData("/config/sort/set?cheap")
        );

        final List<InlineKeyboardButton> keyboardRow3 = new ArrayList<>();
        keyboardRow3.add(
                new InlineKeyboardButton("From Expensive to Cheap")
                        .setCallbackData("/config/sort/set?expensive")
        );

        final List<InlineKeyboardButton> keyboardRow4 = new ArrayList<>();
        keyboardRow4.add(
                new InlineKeyboardButton("Back")
                        .setCallbackData("/config/price")
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

    private String parseSortType(final CallbackQuery callbackQuery) {
        return parseSortType(callbackQuery.getData());
    }

    private String parseSortType(final Message message) {
        return parseSortType(message.getText());
    }

    private String parseSortType(final String data) {
        return data.split("\\?")[1];
    }
}
