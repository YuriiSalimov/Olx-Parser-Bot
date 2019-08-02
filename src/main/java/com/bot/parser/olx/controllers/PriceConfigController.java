/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.controllers;

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
 * PriceController
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@BotController
@BotRequestMapping("/config/price")
public final class PriceConfigController {

    private final ClientService clientService;

    @Autowired
    public PriceConfigController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @BotRequestMapping(method = BotRequestMethod.MESSAGE)
    public SendMessage toPriceConfigMessage(final Update update) {
        return createPriceConfigResponse(update.getMessage().getChatId());
    }

    @BotRequestMapping(method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toPriceConfigCallback(final Update update) {
        return createPriceConfigResponse(update.getCallbackQuery().getMessage().getChatId());
    }

    @BotRequestMapping(value = "/min", method = BotRequestMethod.MESSAGE)
    public SendMessage toMinPriceConfigMessage(final Update update) {
        return createPriceConfigResponse(update.getMessage().getChatId(), "min");
    }

    @BotRequestMapping(value = "/min", method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toMinPriceConfigCallback(final Update update) {
        return createPriceConfigResponse(update.getCallbackQuery().getMessage().getChatId(), "min");
    }

    @BotRequestMapping(value = "/max", method = BotRequestMethod.MESSAGE)
    public SendMessage toMaxPriceConfigMessage(final Update update) {
        return createPriceConfigResponse(update.getMessage().getChatId(), "max");
    }

    @BotRequestMapping(value = "/max", method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage toMaxPriceConfigCallback(final Update update) {
        return createPriceConfigResponse(update.getCallbackQuery().getMessage().getChatId(), "max");
    }

    @BotRequestMapping(value = "/min/set", method = BotRequestMethod.MESSAGE)
    public SendMessage setMinPriceMessage(final Update update) {
        final Message message = update.getMessage();
        final Long id = message.getChatId();
        final long price = parsePrice(message);
        updateMinPrice(id, price);
        return createSetPriceResponse(id, price, "min");
    }

    @BotRequestMapping(value = "/min/set", method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage setMinPriceCallback(final Update update) {
        final CallbackQuery callbackQuery = update.getCallbackQuery();
        final Long id = callbackQuery.getMessage().getChatId();
        final long price = parsePrice(callbackQuery);
        updateMinPrice(id, price);
        return createSetPriceResponse(id, price, "min");
    }

    @BotRequestMapping(value = "/max/set", method = BotRequestMethod.MESSAGE)
    public SendMessage setMaxPriceMessage(final Update update) {
        final Message message = update.getMessage();
        final Long id = message.getChatId();
        final long price = parsePrice(message);
        updateMaxPrice(id, price);
        return createSetPriceResponse(id, price, "max");
    }

    @BotRequestMapping(value = "/max/set", method = BotRequestMethod.CALLBACK_QUERY)
    public SendMessage setMaxPriceCallback(final Update update) {
        final CallbackQuery callbackQuery = update.getCallbackQuery();
        final Long id = callbackQuery.getMessage().getChatId();
        final long price = parsePrice(callbackQuery);
        updateMaxPrice(id, price);
        return createSetPriceResponse(id, price, "max");
    }

    private void updateMinPrice(final Long clientId, final long price) {
        this.clientService.getById(clientId)
                .parseConfig()
                .addPriceFrom(price);
    }

    private void updateMaxPrice(final Long clientId, final long price) {
        this.clientService.getById(clientId)
                .parseConfig()
                .addPriceTo(price);
    }

    private SendMessage createPriceConfigResponse(final Long chatId) {
        return addPriceInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Please, select price to parse:"
                )
        );
    }

    private SendMessage createPriceConfigResponse(final Long chatId, final String priceType) {
        return addPriceInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Now, choose new " + priceType + " price:"
                ),
                priceType
        );
    }

    private SendMessage createSetPriceResponse(
            final Long chatId,
            final long price,
            final String priceType
    ) {
        return addPriceInlineKeyboardButtons(
                new SendMessage(
                        chatId,
                        "Done! Setted new " + priceType + " price: " +
                                price + " UAH"
                ),
                priceType
        );
    }

    private SendMessage addPriceInlineKeyboardButtons(final SendMessage sendMessage) {
        final List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(
                new InlineKeyboardButton("Min price")
                        .setCallbackData("/config/price/min")
        );
        keyboardRow1.add(
                new InlineKeyboardButton("Max Price")
                        .setCallbackData("/config/price/max")
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

    private SendMessage addPriceInlineKeyboardButtons(
            final SendMessage sendMessage,
            final String priceType
    ) {
        final List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(
                new InlineKeyboardButton("0 UAH")
                        .setCallbackData("/config/price/" + priceType + "/set?0")
        );
        keyboardRow1.add(
                new InlineKeyboardButton("50 000 UAH")
                        .setCallbackData("/config/price/" + priceType + "/set?50000")
        );

        final List<InlineKeyboardButton> keyboardRow2 = new ArrayList<>();
        keyboardRow2.add(
                new InlineKeyboardButton("100 000 UAH")
                        .setCallbackData("/config/price/" + priceType + "/set?100000")
        );
        keyboardRow2.add(
                new InlineKeyboardButton("200 000 UAH")
                        .setCallbackData("/config/price/" + priceType + "/set?200000")
        );

        final List<InlineKeyboardButton> keyboardRow3 = new ArrayList<>();
        keyboardRow3.add(
                new InlineKeyboardButton("300 000 UAH")
                        .setCallbackData("/config/price/" + priceType + "/set?300000")
        );
        keyboardRow3.add(
                new InlineKeyboardButton("500 000 UAH")
                        .setCallbackData("/config/price/" + priceType + "/set?500000")
        );

        final List<InlineKeyboardButton> keyboardRow4 = new ArrayList<>();
        keyboardRow4.add(
                new InlineKeyboardButton("700 000 UAH")
                        .setCallbackData("/config/price/" + priceType + "/set?700000")
        );
        keyboardRow4.add(
                new InlineKeyboardButton("1 000 000 UAH")
                        .setCallbackData("/config/price/" + priceType + "/set?1000000")
        );

        final List<InlineKeyboardButton> keyboardRow5 = new ArrayList<>();
        keyboardRow5.add(
                new InlineKeyboardButton("Back")
                        .setCallbackData("/config/price")
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

    private long parsePrice(final CallbackQuery callbackQuery) {
        return parsePrice(callbackQuery.getData());
    }

    private long parsePrice(final Message message) {
        return parsePrice(message.getText());
    }

    private long parsePrice(final String data) {
        return Long.parseLong(data.split("\\?")[1]);
    }
}
