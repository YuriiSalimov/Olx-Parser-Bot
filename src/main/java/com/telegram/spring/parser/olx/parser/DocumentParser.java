/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * OlxDocumentParser
 *
 * @author Yurii Salimov
 * @since 1.0
 */
final class DocumentParser implements Parser<Document> {

    private final ParseUrl url;
    private final UrlParams urlParams;

    DocumentParser(
            final ParseUrl url,
            final UrlParams urlParams
    ) {
        this.url = url;
        this.urlParams = urlParams;
    }

    @Override
    public Document parse() {
        try {
            return Jsoup.connect(this.url.toString())
                    .userAgent(this.urlParams.getUseragent())
                    .referrer(this.urlParams.getReferrer())
                    .get();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
