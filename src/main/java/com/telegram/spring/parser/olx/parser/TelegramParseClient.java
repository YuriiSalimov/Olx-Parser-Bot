/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.parser;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * TelegramParseClient
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@Slf4j
public final class TelegramParseClient implements ParseClient<Collection<Offer>> {

    private final Long id;
    private final ParserBuilder<Collection<Offer>> parserBuilder;
    private final TelegramLongPollingBot telegramBot;

    private final Collection<Offer> offers = new ArrayList<>();
    private volatile int numberOfResult = 5;
    private volatile long delayTime = 30; // in minutes
    private volatile boolean enabled = true;

    public TelegramParseClient(
            final Long id,
            final ParserBuilder<Collection<Offer>> parserBuilder,
            final TelegramLongPollingBot telegramBot
    ) {
        this.id = id;
        this.parserBuilder = parserBuilder;
        this.telegramBot = telegramBot;
    }

    @Override
    public void run() {
        sendToTelegram(
                toSendMessages(
                        uniqueOffers(
                                parseOffers()
                        )
                )
        );
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setNumberOfResult(int number) {
        if (number > 0) {
            this.numberOfResult = number;
        }
    }

    @Override
    public void setDelayTime(long timeInMinutes) {
        if (timeInMinutes > 0) {
            this.delayTime = timeInMinutes;
        }
    }

    @Override
    public long getDelayTime() {
        return this.delayTime;
    }

    @Override
    public ParserBuilder<Collection<Offer>> parseConfig() {
        return this.parserBuilder;
    }

    @Override
    public void clear() {
        this.offers.clear();
    }

    @Override
    public boolean isEnable() {
        return this.enabled;
    }

    @Override
    public String toString() {
        return "Client{id=" + this.id + "}";
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final TelegramParseClient that = (TelegramParseClient) object;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return 18 + Objects.hashCode(this.id);
    }

    private Collection<Offer> parseOffers() {
        return this.parserBuilder.build().parse();
    }

    private Collection<SendMessage> toSendMessages(final Collection<Offer> offers) {
        return offers.stream()
                .map(this::toSendMessages)
                .collect(Collectors.toList());
    }

    private SendMessage toSendMessages(final Offer offer) {
        return new SendMessage(this.id, offer.toString());
    }

    private void sendToTelegram(final Collection<SendMessage> messages) {
        messages.forEach(this::sendToTelegram);
    }

    private void sendToTelegram(final SendMessage messages) {
        try {
            this.telegramBot.execute(messages);
            this.enabled = true;
        } catch (TelegramApiException ex) {
            log.error(ex.getMessage(), ex);
            this.enabled = false;
        }
    }

    private Collection<Offer> uniqueOffers(final Collection<Offer> newOffers) {
        final Collection<Offer> uniqueOffers = new ArrayList<>();
        int index = 0;
        for (Offer offer : newOffers) {
            if (!this.offers.contains(offer)) {
                uniqueOffers.add(offer);
                index++;
                if (index >= this.numberOfResult) {
                    break;
                }
            }
        }
        clear();
        this.offers.addAll(newOffers);
        return uniqueOffers;
    }
}
