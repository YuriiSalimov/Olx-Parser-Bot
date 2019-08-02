/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.dao;

import com.bot.parser.olx.parser.ParseClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ParseClientRepositoryImpl
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@Repository
@Scope("singleton")
public final class ParseClientRepositoryImpl implements ParseClientRepository {

    private final Map<Long, ParseClient<?>> clientContainer = new ConcurrentHashMap<>();

    public ParseClientRepositoryImpl() {
        new DisabledClientCleaner(this.clientContainer).start();
    }

    @Override
    public void add(final ParseClient<?> parseClient) {
        this.clientContainer.put(parseClient.getId(), parseClient);
    }

    @Override
    public ParseClient<?> getById(final Long id) {
        return this.clientContainer.get(id);
    }

    @Override
    public ParseClient<?> deleteById(final Long id) {
        return this.clientContainer.remove(id);
    }

    @Override
    public void deleteAll() {
        this.clientContainer.clear();
    }

    @Override
    public boolean isExistById(final Long id) {
        return this.clientContainer.containsKey(id);
    }
}
