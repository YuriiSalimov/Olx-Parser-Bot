/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.parser;

/**
 * DefaultUrlParams
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public interface DefaultUrlParams {

    String getSection();

    String getCategory();

    String getSubcategory();

    String getType();

    String getCity();

    int getDistance();

    long getPriceFrom();

    long getPriceTo();

    int getNumberOfRoomsFrom();

    int getNumberOfRoomsTo();

    String getSort();

    boolean isWithPromotedOffers();
}
