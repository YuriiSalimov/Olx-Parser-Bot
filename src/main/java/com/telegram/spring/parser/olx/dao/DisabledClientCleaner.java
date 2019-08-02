/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.dao;

import com.telegram.spring.parser.olx.parser.ParseClient;

import java.util.Map;

/**
 * DisabledClientCleaner
 *
 * @author Yurii Salimov
 * @since 1.0
 */
final class DisabledClientCleaner extends Thread {

    private final static long DELAY_TIME = 12 * 60 * 60 * 1000;

    private final Map<Long, ParseClient<?>> clientContainer;

    DisabledClientCleaner(final Map<Long, ParseClient<?>> clientContainer) {
        this.clientContainer = clientContainer;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            removeDisabledClients();
            sleep();
        }
    }

    private void removeDisabledClients() {
        for (ParseClient<?> client : this.clientContainer.values()) {
            if (!client.isEnable()) {
                this.clientContainer.remove(client.getId());
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(DELAY_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
