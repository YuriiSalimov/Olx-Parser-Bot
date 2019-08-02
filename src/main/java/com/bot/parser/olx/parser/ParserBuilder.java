/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.parser;

/**
 * ParserBuilder
 *
 * @author Yurii Salimov
 * @since 1.0
 */
public interface ParserBuilder<T> {

    Parser<T> build();

    ParserBuilder<T> addSection(String section);

    ParserBuilder<T> addCategory(String category);

    ParserBuilder<T> addSubcategory(String subcategory);

    ParserBuilder<T> addType(String objectType);

    ParserBuilder<T> addCity(String city);

    ParserBuilder<T> addDistance(int distanceInKm);

    ParserBuilder<T> addPriceFrom(long priceFromInUAH);

    ParserBuilder<T> addPriceTo(long priceToInUAH);

    ParserBuilder<T> addNamberOfRoomsFrom(int numberOfRooms);

    ParserBuilder<T> addNamberOfRoomsTo(int numberOfRooms);

    ParserBuilder<T> addWithPromotedOffers(boolean promoted);

    ParserBuilder<T> sortByCheap();

    ParserBuilder<T> sortByExpensive();

    ParserBuilder<T> sortByDate();
}
