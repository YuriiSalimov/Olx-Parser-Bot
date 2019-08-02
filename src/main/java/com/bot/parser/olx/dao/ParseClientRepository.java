/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.dao;

import com.bot.parser.olx.parser.ParseClient;

/**
 * ParseClientRepository
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public interface ParseClientRepository {

    void add(ParseClient<?> parseClient);

    ParseClient<?> getById(Long id);

    ParseClient<?> deleteById(Long id);

    void deleteAll();

    boolean isExistById(Long id);
}
