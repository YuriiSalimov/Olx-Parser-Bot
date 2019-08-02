/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.parser;

import java.util.Collection;

/**
 * ParserBuilder
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public final class OffersParserBuilder implements ParserBuilder<Collection<Offer>> {

    private final UrlParams urlParams;
    private final ParseSelectors parseSelectors;

    private String section;
    private String category;
    private String subcategory;
    private String type;
    private String city;
    private int distance;
    private long priceFrom; // in UAH
    private long priceTo; // in UAH
    private int numberOfRoomsFrom;
    private int numberOfRoomsTo;
    private String sort;
    private boolean withPromotedOffers;

    public OffersParserBuilder(
            final UrlParams urlParams,
            final DefaultUrlParams defaultUrlParams,
            final ParseSelectors parseSelectors
    ) {
        this.urlParams = urlParams;
        this.parseSelectors = parseSelectors;

        this.section = defaultUrlParams.getSection();
        this.category = defaultUrlParams.getCategory();
        this.subcategory = defaultUrlParams.getSubcategory();
        this.type = defaultUrlParams.getType();
        this.city = defaultUrlParams.getCity();
        this.distance = defaultUrlParams.getDistance();
        this.priceFrom = defaultUrlParams.getPriceFrom();
        this.priceTo = defaultUrlParams.getPriceTo();
        this.numberOfRoomsFrom = defaultUrlParams.getNumberOfRoomsFrom();
        this.numberOfRoomsTo = defaultUrlParams.getNumberOfRoomsTo();
        this.sort = defaultUrlParams.getSort();
        this.withPromotedOffers = defaultUrlParams.isWithPromotedOffers();
    }

    public Parser<Collection<Offer>> build() {
        return new DocumentOfferParser(
                new DocumentParser(
                        new ParseUrl(
                                this.urlParams,
                                this.urlParams.getBaseUrl(),
                                this.section,
                                this.category,
                                this.subcategory,
                                this.type,
                                this.city,
                                this.distance,
                                this.priceFrom,
                                this.priceTo,
                                this.numberOfRoomsFrom,
                                this.numberOfRoomsTo,
                                this.sort
                        ),
                        this.urlParams
                ),
                this.parseSelectors,
                this.withPromotedOffers
        );
    }

    @Override
    public OffersParserBuilder addSection(final String section) {
        this.section = section;
        return this;
    }

    @Override
    public OffersParserBuilder addCategory(final String category) {
        this.category = category;
        return this;
    }

    @Override
    public OffersParserBuilder addSubcategory(final String subcategory) {
        this.subcategory = subcategory;
        return this;
    }

    @Override
    public OffersParserBuilder addType(final String objectType) {
        this.type = objectType;
        return this;
    }

    @Override
    public OffersParserBuilder addCity(final String city) {
        this.city = city;
        return this;
    }

    @Override
    public OffersParserBuilder addDistance(final int distanceInKm) {
        this.distance = distanceInKm;
        return this;
    }

    @Override
    public OffersParserBuilder addPriceFrom(final long priceFromInUAH) {
        this.priceFrom = priceFromInUAH;
        return this;
    }

    @Override
    public OffersParserBuilder addPriceTo(final long priceToInUAH) {
        this.priceTo = priceToInUAH;
        return this;
    }

    @Override
    public OffersParserBuilder addNamberOfRoomsFrom(final int numberOfRooms) {
        this.numberOfRoomsFrom = numberOfRooms;
        return this;
    }

    @Override
    public OffersParserBuilder addNamberOfRoomsTo(final int numberOfRooms) {
        this.numberOfRoomsTo = numberOfRooms;
        return this;
    }

    @Override
    public OffersParserBuilder addWithPromotedOffers(boolean promoted) {
        this.withPromotedOffers = promoted;
        return this;
    }

    @Override
    public OffersParserBuilder sortByCheap() {
        this.sort = this.urlParams.getSortByPriceAsc();
        return this;
    }

    @Override
    public OffersParserBuilder sortByExpensive() {
        this.sort = this.urlParams.getSortByPriceDesc();
        return this;
    }

    @Override
    public OffersParserBuilder sortByDate() {
        this.sort = this.urlParams.getSortByDate();
        return this;
    }
}
