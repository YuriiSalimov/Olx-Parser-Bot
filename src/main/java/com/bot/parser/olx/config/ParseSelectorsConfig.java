/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.config;

import com.bot.parser.olx.parser.ParseSelectors;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * ParseSelectorsConfig
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@Getter
@Configuration
public class ParseSelectorsConfig implements ParseSelectors {

    @Value("${olx.offer.parse.selector}")
    private String offerSelector;

    @Value("${olx.not-promoted-offer.parse.selector}")
    private String notPromotedOfferSelector;

    @Value("${olx.link.parse.selector}")
    private String linkSelector;

    @Value("${olx.price.parse.selector}")
    private String priceSelector;

    @Value("${olx.img.parse.selector}")
    private String imgSelector;

    @Value("${olx.location.parse.selector}")
    private String locationSelector;

    @Bean
    @Scope("singleton")
    public ParseSelectors parseSelectors() {
        return this;
    }
}
