/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.config;

import com.telegram.spring.parser.olx.parser.DefaultUrlParams;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * DefaultUrlParamsConfig
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@Getter
@Configuration
public class DefaultUrlParamsConfig implements DefaultUrlParams {

    @Value("${olx.default.section}")
    private String section;

    @Value("${olx.default.category}")
    private String category;

    @Value("${olx.default.subcategory}")
    private String subcategory;

    @Value("${olx.default.type}")
    private String type;

    @Value("${olx.default.city}")
    private String city;

    @Value("${olx.default.distance}")
    private int distance;

    @Value("${olx.default.price-from}")
    private long priceFrom;

    @Value("${olx.default.price-to}")
    private long priceTo;

    @Value("${olx.default.number-of-rooms-from}")
    private int numberOfRoomsFrom;

    @Value("${olx.default.number-of-rooms-to}")
    private int numberOfRoomsTo;

    @Value("${olx.default.with-promoted-offers}")
    private boolean withPromotedOffers;

    @Value("${olx.default.sort}")
    private String sort;

    @Bean
    @Scope("singleton")
    public DefaultUrlParams defaultUrlParams() {
        return this;
    }
}
