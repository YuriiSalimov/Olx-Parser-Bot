/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.telegram;

import java.util.function.Function;

/**
 * UpdateHandle
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public interface Handle<Request, Response> extends Function<Request, Response> {
}
