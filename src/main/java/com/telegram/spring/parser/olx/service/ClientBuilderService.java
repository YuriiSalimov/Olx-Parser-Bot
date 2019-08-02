/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.service;

import com.telegram.spring.parser.olx.parser.ParseClient;

import java.util.function.Function;

/**
 * ParseClientBuilderService
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public interface ClientBuilderService extends Function<Long, ParseClient<?>> {
}
