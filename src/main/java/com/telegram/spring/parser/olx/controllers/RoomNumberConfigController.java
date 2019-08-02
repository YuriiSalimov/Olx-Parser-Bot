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
 * NumberConigController
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@BotController
@BotRequestMapping("/config/rooms-number")
public final class RoomNumberConfigController {

    private final ClientService clientService;

    @Autowired
    public RoomNumberConfigController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @BotRequestMapping(method = BotRequestMethod.MESSAGE)
    public SendMessage toRoomNumberConfigMessage(final Update update) {
        return createRoomNumberConfigResponse(update.getMessage().getChatId());
    }

    @BotRequestMapping(method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toRoomNumberConfigCallback(final Update update) {
        return createRoomNumberConfigResponse(update.getCallbackQuery().getMessage().getChatId());
    }

    @BotRequestMapping(value = "/from", method = BotRequestMethod.MESSAGE)
    public SendMessage toFromRoomNumberConfigMessage(final Update update) {
        return createRoomNumberConfigResponse(update.getMessage().getChatId(), "from");
    }

    @BotRequestMapping(value = "/from", method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toFromRoomNumberConfigCallback(final Update update) {
        return createRoomNumberConfigResponse(update.getCallbackQuery().getMessage().getChatId(), "from");
    }

    @BotRequestMapping(value = "/to", method = BotRequestMethod.MESSAGE)
    public SendMessage toToRoomNumberConfigMessage(final Update update) {
        return createRoomNumberConfigResponse(update.getMessage().getChatId(), "to");
    }

    @BotRequestMapping(value = "/to", method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toToRoomNumberConfigCallback(final Update update) {
        return createRoomNumberConfigResponse(update.getCallbackQuery().getMessage().getChatId(), "to");
    }

    @BotRequestMapping(value = "/from/set", method = BotRequestMethod.MESSAGE)
    public SendMessage setFromRoomNumberMessage(final Update update) {
        final Message message = update.getMessage();
        final Long id = message.getChatId();
        final int roomNumber = parseNumber(message);
        updateFromRoomNumber(id, roomNumber);
        return createSetRoomNumberResponse(id, roomNumber, "from");
    }

    @BotRequestMapping(value = "/from/set", method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage setFromRoomNumberCallback(final Update update) {
        final CallbackQuery callbackQuery = update.getCallbackQuery();
        final Long id = callbackQuery.getMessage().getChatId();
        final int roomNumber = parseNumber(callbackQuery);
        updateFromRoomNumber(id, roomNumber);
        return createSetRoomNumberResponse(id, roomNumber, "from");
    }

    @BotRequestMapping(value = "/to/set", method = BotRequestMethod.MESSAGE)
    public SendMessage setToRoomNumberMessage(final Update update) {
        final Message message = update.getMessage();
        final Long id = message.getChatId();
        final int roomNumber = parseNumber(message);
        updateToRoomNumber(id, roomNumber);
        return createSetRoomNumberResponse(id, roomNumber, "to");
    }

    @BotRequestMapping(value = "/to/set", method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage setToRoomNumberCallback(final Update update) {
        final CallbackQuery callbackQuery = update.getCallbackQuery();
        final Long id = callbackQuery.getMessage().getChatId();
        final int roomNumber = parseNumber(callbackQuery);
        updateToRoomNumber(id, roomNumber);
        return createSetRoomNumberResponse(id, roomNumber, "to");
    }

    private void updateFromRoomNumber(final Long clientId, final int roomNumber) {
        this.clientService.getById(clientId)
                .parseConfig()
                .addNamberOfRoomsFrom(roomNumber);
    }

    private void updateToRoomNumber(final Long clientId, final int roomNumber) {
        this.clientService.getById(clientId)
                .parseConfig()
                .addNamberOfRoomsTo(roomNumber);
    }

    private SendMessage createRoomNumberConfigResponse(final Long chatId) {
        return addRoomNumberInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Please, choose bound " +
                                "of the room number range to parseConfig:"
                )
        );
    }

    private SendMessage createRoomNumberConfigResponse(final Long chatId, final String numberType) {
        return addRoomNumberInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Now, choose new \"" + numberType + "\" " +
                                "bound of the room number range:"
                ),
                numberType
        );
    }

    private SendMessage createSetRoomNumberResponse(
            final Long chatId,
            final int roomNumber,
            final String numberType
    ) {
        return addRoomNumberInlineKeyboardButtons(
                new SendMessage(chatId,
                        "Done! Setted new \"" + numberType + "\" " +
                                "bound of the room number range: " + roomNumber),
                numberType
        );
    }

    private SendMessage addRoomNumberInlineKeyboardButtons(final SendMessage sendMessage) {
        final List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(
                new InlineKeyboardButton("From number")
                        .setCallbackData("/config/rooms-number/from")
        );
        keyboardRow1.add(
                new InlineKeyboardButton("To number")
                        .setCallbackData("/config/rooms-number/to")
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

    private SendMessage addRoomNumberInlineKeyboardButtons(
            final SendMessage sendMessage,
            final String numberType
    ) {
        final List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(
                new InlineKeyboardButton("1")
                        .setCallbackData("/config/rooms-number/" + numberType + "/set?1")
        );
        keyboardRow1.add(
                new InlineKeyboardButton("2")
                        .setCallbackData("/config/rooms-number/" + numberType + "/set?2")
        );

        final List<InlineKeyboardButton> keyboardRow2 = new ArrayList<>();
        keyboardRow2.add(
                new InlineKeyboardButton("3")
                        .setCallbackData("/config/rooms-number/" + numberType + "/set?3")
        );
        keyboardRow2.add(
                new InlineKeyboardButton("4")
                        .setCallbackData("/config/rooms-number/" + numberType + "/set?4")
        );

        final List<InlineKeyboardButton> keyboardRow3 = new ArrayList<>();
        keyboardRow3.add(
                new InlineKeyboardButton("5")
                        .setCallbackData("/config/rooms-number/" + numberType + "/set?5")
        );
        keyboardRow3.add(
                new InlineKeyboardButton("6")
                        .setCallbackData("/config/rooms-number/" + numberType + "/set?6")
        );

        final List<InlineKeyboardButton> keyboardRow4 = new ArrayList<>();
        keyboardRow4.add(
                new InlineKeyboardButton("Back")
                        .setCallbackData("/config/rooms-number")
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

    private int parseNumber(final CallbackQuery callbackQuery) {
        return parseNumber(callbackQuery.getData());
    }

    private int parseNumber(final Message message) {
        return parseNumber(message.getText());
    }

    private int parseNumber(final String data) {
        return Integer.parseInt(data.split("\\?")[1]);
    }
}
