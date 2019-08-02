/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.parser;

/**
 * ParseClient
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public interface ParseClient<T> extends Runnable {

    Long getId();

    void setNumberOfResult(int number);

    void setDelayTime(long timeInMinutes);

    long getDelayTime();

    boolean isEnable();

    void clear();

    ParserBuilder<T> parseConfig();
}
