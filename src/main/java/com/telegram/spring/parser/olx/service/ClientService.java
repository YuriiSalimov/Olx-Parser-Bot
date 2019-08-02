/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.service;

import com.telegram.spring.parser.olx.parser.ParseClient;

/**
 * ParseClientService
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public interface ClientService {

    ParseClient<?> createNew(Long id);

    ParseClient<?> getById(Long id);

    boolean isExistById(Long id);

    boolean isExist(ParseClient<?> client);

    ParseClient<?> deleteById(Long id);

    void deleteAll();
}
