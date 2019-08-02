/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.controllers;

import com.telegram.spring.parser.olx.service.ClientService;
import com.telegram.spring.parser.olx.service.ExecutorClientService;
import com.telegram.spring.parser.olx.telegram.annotation.BotController;
import com.telegram.spring.parser.olx.telegram.annotation.BotRequestMapping;
import com.telegram.spring.parser.olx.telegram.annotation.BotRequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * StopController
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@BotController
@BotRequestMapping("/pause")
public final class PauseController {

    private final ClientService clientService;
    private final ExecutorClientService executorService;

    @Autowired
    public PauseController(
            final ClientService clientService,
            final ExecutorClientService executorService
    ) {
        this.clientService = clientService;
        this.executorService = executorService;
    }

    @BotRequestMapping(method = BotRequestMethod.MESSAGE)
    public SendMessage pauseBotMessage(final Update update) {
        final Long id = update.getMessage().getChatId();
        pauseClientById(id);
        return createPauseResponses(id);
    }

    @BotRequestMapping(method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage pauseBotCallback(final Update update) {
        final Long id = update.getCallbackQuery().getMessage().getChatId();
        pauseClientById(id);
        return createPauseResponses(id);
    }

    private void pauseClientById(final Long id) {
        this.executorService.stop(
                this.clientService.getById(id)
        );
    }

    private SendMessage createPauseResponses(final Long chatId) {
        return addInlineKeyboardButtons(
                new SendMessage(
                        chatId, "Paused...")
        );
    }

    private SendMessage addInlineKeyboardButtons(final SendMessage sendMessage) {
        final List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(
                new InlineKeyboardButton()
                        .setText("Run")
                        .setCallbackData("/run")
        );

        final List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow1);

        return sendMessage.setReplyMarkup(
                new InlineKeyboardMarkup().setKeyboard(keyboardRows)
        );
    }
}
