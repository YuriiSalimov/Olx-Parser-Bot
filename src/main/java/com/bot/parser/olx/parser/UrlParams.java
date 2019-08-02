/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.parser;

/**
 * UrlParams
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public interface UrlParams {

    String getBaseUrl();

    String getUseragent();

    String getReferrer();

    String getSearchPriceFrom();

    String getSearchPriceTo();

    String getSearchNumberOfRoomsFrom();

    String getSearchNumberOfRoomsTo();

    String getSearchDistance();

    String getSearchSort();

    String getSortByPriceAsc();

    String getSortByPriceDesc();

    String getSortByDate();
}
