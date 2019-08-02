/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.util;

/**
 * Container
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public interface Container<Key, T> {

    void add(Key key, T t);

    T get(Key key);
}
