/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.parser;

import lombok.Data;

import java.io.Serializable;

/**
 * Offer
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@Data
public final class Offer implements Serializable {

    private final String title;
    private final String url;
    private final String img;
    private final String location;
    private final String price;

    @Override
    public String toString() {
        return this.title + "\n" +
                this.price + "\n" +
                this.location + "\n" +
                this.url;
    }
}
