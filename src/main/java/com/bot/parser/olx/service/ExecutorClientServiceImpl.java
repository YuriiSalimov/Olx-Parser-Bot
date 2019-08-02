/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.service;

import com.bot.parser.olx.parser.ParseClient;
import com.bot.parser.olx.util.ObjectValidator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorClientServiceImpl
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@Service
@Scope("singleton")
public final class ExecutorClientServiceImpl implements ExecutorClientService {

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
    private final Map<Long, ScheduledFuture<?>> scheduledFutures = new HashMap<>();

    @Override
    public void start(final ParseClient<?> client) throws IllegalArgumentException {
        checkClient(client);
        startClientScheduledFuture(client);
    }

    @Override
    public void stop(final ParseClient<?> client) throws IllegalArgumentException {
        checkClient(client);
        stopClientScheduledFuture(client);
    }

    @Override
    public void restart(final ParseClient<?> client) throws IllegalArgumentException {
        checkClient(client);
        stopClientScheduledFuture(client);
        startClientScheduledFuture(client);
    }

    private void startClientScheduledFuture(final ParseClient<?> client) {
        if (!this.scheduledFutures.containsKey(client.getId())) {
            this.scheduledFutures.put(
                    client.getId(),
                    this.executorService.scheduleWithFixedDelay(
                            client,
                            0, client.getDelayTime(),
                            TimeUnit.MINUTES
                    )
            );
        }
    }

    private void stopClientScheduledFuture(final ParseClient<?> client) {
        if (this.scheduledFutures.containsKey(client.getId())) {
            this.scheduledFutures.get(client.getId()).cancel(true);
            this.scheduledFutures.remove(client.getId());
            client.clear();
        }
    }

    private void checkClient(final ParseClient<?> client) throws IllegalArgumentException {
        if (ObjectValidator.isNull(client)) {
            throw new IllegalArgumentException(
                    "Input client instance must be not null!"
            );
        }
    }
}
