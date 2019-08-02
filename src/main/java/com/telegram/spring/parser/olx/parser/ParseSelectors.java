/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.parser;

/**
 * ParseSelectors
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public interface ParseSelectors {

    String getOfferSelector();

    String getNotPromotedOfferSelector();

    String getLinkSelector();

    String getPriceSelector();

    String getImgSelector();

    String getLocationSelector();
}
