/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.parser;

/**
 * Parser
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public interface Parser<T> {

    T parse();
}
