/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.parser;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * ParseUrl
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@AllArgsConstructor
@EqualsAndHashCode
public final class ParseUrl {

    private final UrlParams urlParams;
    private final String rootUrl;
    private final String section;
    private final String category;
    private final String subcategory;
    private final String type;
    private final String city;
    private final int distance;
    private final long priceFrom; // in UAH
    private final long priceTo; // in UAH
    private final int numberOfRoomsFrom;
    private final int numberOfRoomsTo;
    private final String sort;

    @Override
    public String toString() {
        final StringBuilder url = new StringBuilder(this.rootUrl);
        if (this.section != null) {
            url.append("/").append(this.section);
            if (this.category != null) {
                url.append("/").append(this.category);
                if (this.subcategory != null) {
                    url.append("/").append(this.subcategory);
                    if (this.type != null) {
                        url.append("/").append(this.type);
                    }
                }
            }
        }
        if (this.city != null) {
            url.append("/").append(this.city);
        }
        url.append("?");
        if (this.priceFrom > 0) {
            url.append(this.urlParams.getSearchPriceFrom())
                    .append("=")
                    .append(this.priceFrom)
                    .append("&");
        }
        if (this.priceTo > 0) {
            url.append(this.urlParams.getSearchPriceTo())
                    .append("=")
                    .append(this.priceTo)
                    .append("&");
        }
        if (this.numberOfRoomsFrom > 0) {
            url.append(this.urlParams.getSearchNumberOfRoomsFrom())
                    .append("=")
                    .append(this.numberOfRoomsFrom)
                    .append("&");
        }
        if (this.numberOfRoomsTo > 0) {
            url.append(this.urlParams.getSearchNumberOfRoomsTo())
                    .append("=")
                    .append(this.numberOfRoomsTo)
                    .append("&");
        }
        if (this.distance > 0) {
            url.append(this.urlParams.getSearchDistance())
                    .append("=")
                    .append(this.distance)
                    .append("&");
        }
        if (this.sort != null) {
            url.append(this.urlParams.getSearchSort())
                    .append("=")
                    .append(this.sort);
        }
        return url.toString();
    }
}
