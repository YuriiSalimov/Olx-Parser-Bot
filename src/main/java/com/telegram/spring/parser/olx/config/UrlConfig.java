/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.config;

import com.telegram.spring.parser.olx.parser.UrlParams;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * UrlConfig
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@Getter
@Configuration
public class UrlConfig implements UrlParams {

    @Value("${olx.base.url}")
    private String baseUrl;

    @Value("${olx.user.agent}")
    private String useragent;

    @Value("${olx.referrer.url}")
    private String referrer;

    @Value("${olx.price-from.search.param}")
    private String searchPriceFrom;

    @Value("${olx.price-to.search.param}")
    private String searchPriceTo;

    @Value("${olx.number-of-rooms-from.search.param}")
    private String searchNumberOfRoomsFrom;

    @Value("${olx.number-of-rooms-to.search.param}")
    private String searchNumberOfRoomsTo;

    @Value("${olx.distance.search.param}")
    private String searchDistance;

    @Value("${olx.sort.search.param}")
    private String searchSort;

    @Value("${olx.price-asc.sort.type}")
    private String sortByPriceAsc;

    @Value("${olx.price-desc.sort.type}")
    private String sortByPriceDesc;

    @Value("${olx.date.sort.type}")
    private String sortByDate;

    @Bean
    @Scope("singleton")
    public UrlParams urlParams() {
        return this;
    }
}
