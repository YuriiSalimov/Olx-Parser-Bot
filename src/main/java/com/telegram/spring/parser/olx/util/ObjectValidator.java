/*
 * Created by Yurii Salimov
 * Copyright (c) 2017-2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.util;

import java.util.Collection;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * ObjectValidator - util-class implements a set of methods
 * for validating an incoming object.
 */
public final class ObjectValidator {

    private ObjectValidator() {
    }

    /**
     * Returns true if the provided reference is null,
     * otherwise returns false.
     *
     * @param input a reference to be checked against null
     * @return true if the provided reference is null,
     * otherwise false
     */
    public static boolean isNull(final Object input) {
        return (input == null);
    }

    /**
     * Returns true if the provided reference is not null,
     * otherwise returns false.
     *
     * @param input a reference to be checked against not null
     * @return true if the provided reference is not null,
     * otherwise false
     */
    public static boolean isNotNull(final Object input) {
        return !isNull(input);
    }

    /**
     * Returns true if the provided collection is null
     * or it contains no elements.
     *
     * @param input a collection to be checked
     * @return true if this collection is null
     * or it contains no elements
     */
    public static boolean isEmpty(final Collection input) {
        return isNull(input) || input.isEmpty();
    }

    /**
     * Returns true if the provided collection is not null
     * and it contains some elements.
     *
     * @param input a collection to be checked
     * @return true if this collection is not null
     * and it contains some elements
     */
    public static boolean isNotEmpty(final Collection input) {
        return !isEmpty(input);
    }

    /**
     * Returns true if the provided map is null
     * or it contains no elements.
     *
     * @param input a map to be checked
     * @return true if this map is null
     * or it contains no elements
     */
    public static boolean isEmpty(final Map input) {
        return isNull(input) || input.isEmpty();
    }

    /**
     * Returns true if the provided map is not null
     * and it contains some elements.
     *
     * @param input a map to be checked
     * @return true if this map is not null
     * and it contains some elements
     */
    public static boolean isNotEmpty(final Map input) {
        return !isEmpty(input);
    }

    /**
     * Returns true if the provided string is null,
     * empty or whitespace, false otherwise.
     *
     * @param input a string to be checked
     * @return true if the incoming string is null,
     * empty or whitespace, false otherwise.
     */
    public static boolean isEmpty(final String input) {
        return isBlank(input);
    }

    /**
     * Returns true if the provided string is not null and
     * not empty or whitespace, false otherwise.
     *
     * @param input a string to be checked
     * @return true if the incoming string is not null and
     * not empty or whitespace, false otherwise.
     */
    public static boolean isNotEmpty(final String input) {
        return !isEmpty(input);
    }

    /**
     * Returns true if the provided array is null or empty,
     * false otherwise.
     *
     * @param input an object array to be checked
     * @return true if the provided array is null or empty,
     * false otherwise.
     */
    public static boolean isEmpty(final Object[] input) {
        return isNull(input) || (input.length == 0);
    }

    /**
     * Returns true if the provided array is not null and not empty,
     * false otherwise.
     *
     * @param input an object array to be checked
     * @return true if the provided array is not null and not empty,
     * false otherwise.
     */
    public static boolean isNotEmpty(final Object[] input) {
        return !isEmpty(input);
    }

    /**
     * Returns true if the provided byte array is null or empty,
     * false otherwise.
     *
     * @param input a byte array to be checked
     * @return true if the provided array is null or empty,
     * false otherwise.
     */
    public static boolean isEmpty(final byte[] input) {
        return isNull(input) || (input.length == 0);
    }

    /**
     * Returns true if the provided byte array is not null and not empty,
     * false otherwise.
     *
     * @param input a byte array to be checked
     * @return true if the provided array is not null and not empty,
     * false otherwise.
     */
    public static boolean isNotEmpty(final byte[] input) {
        return !isEmpty(input);
    }

    /**
     * Returns true if the provided short array is null or empty,
     * false otherwise.
     *
     * @param input a short array to be checked
     * @return true if the provided array is null or empty,
     * false otherwise.
     */
    public static boolean isEmpty(final short[] input) {
        return isNull(input) || (input.length == 0);
    }

    /**
     * Returns true if the provided short array is not null and not empty,
     * false otherwise.
     *
     * @param input a short array to be checked
     * @return true if the provided array is not null and not empty,
     * false otherwise.
     */
    public static boolean isNotEmpty(final short[] input) {
        return !isEmpty(input);
    }

    /**
     * Returns true if the provided char array is null or empty,
     * false otherwise.
     *
     * @param input a char array to be checked
     * @return true if the provided array is null or empty,
     * false otherwise.
     */
    public static boolean isEmpty(final char[] input) {
        return isNull(input) || (input.length == 0);
    }

    /**
     * Returns true if the provided char array is not null and not empty,
     * false otherwise.
     *
     * @param input a char array to be checked
     * @return true if the provided array is not null and not empty,
     * false otherwise.
     */
    public static boolean isNotEmpty(final char[] input) {
        return !isEmpty(input);
    }

    /**
     * Returns true if the provided int array is null or empty,
     * false otherwise.
     *
     * @param input a int array to be checked
     * @return true if the provided array is null or empty,
     * false otherwise.
     */
    public static boolean isEmpty(final int[] input) {
        return isNull(input) || (input.length == 0);
    }

    /**
     * Returns true if the provided int array is not null and not empty,
     * false otherwise.
     *
     * @param input a int array to be checked
     * @return true if the provided array is not null and not empty,
     * false otherwise.
     */
    public static boolean isNotEmpty(final int[] input) {
        return !isEmpty(input);
    }

    /**
     * Returns true if the provided long array is null or empty,
     * false otherwise.
     *
     * @param input a long array to be checked
     * @return true if the provided array is null or empty,
     * false otherwise.
     */
    public static boolean isEmpty(final long[] input) {
        return isNull(input) || (input.length == 0);
    }

    /**
     * Returns true if the provided long array is not null and not empty,
     * false otherwise.
     *
     * @param input a long array to be checked
     * @return true if the provided array is not null and not empty,
     * false otherwise.
     */
    public static boolean isNotEmpty(final long[] input) {
        return !isEmpty(input);
    }

    /**
     * Returns true if the provided float array is null or empty,
     * false otherwise.
     *
     * @param input a float array to be checked
     * @return true if the provided array is null or empty,
     * false otherwise.
     */
    public static boolean isEmpty(final float[] input) {
        return isNull(input) || (input.length == 0);
    }

    /**
     * Returns true if the provided float array is not null and not empty,
     * false otherwise.
     *
     * @param input a float array to be checked
     * @return true if the provided array is not null and not empty,
     * false otherwise.
     */
    public static boolean isNotEmpty(final float[] input) {
        return !isEmpty(input);
    }

    /**
     * Returns true if the provided double array is null or empty,
     * false otherwise.
     *
     * @param input a double array to be checked
     * @return true if the provided array is null or empty,
     * false otherwise.
     */
    public static boolean isEmpty(final double[] input) {
        return isNull(input) || (input.length == 0);
    }

    /**
     * Returns true if the provided double array is not null and not empty,
     * false otherwise.
     *
     * @param input a double array to be checked
     * @return true if the provided array is not null and not empty,
     * false otherwise.
     */
    public static boolean isNotEmpty(final double[] input) {
        return !isEmpty(input);
    }
}
