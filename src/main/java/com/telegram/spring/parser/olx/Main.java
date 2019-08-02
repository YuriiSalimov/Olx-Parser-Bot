/*
 * Created by Yurii Salimov, 01.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

/**
 * Main
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@SpringBootApplication
public class Main {

    public static void main(final String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(Main.class, args);
    }
}
