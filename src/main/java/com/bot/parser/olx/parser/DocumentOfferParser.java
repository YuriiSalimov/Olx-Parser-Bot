/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.bot.parser.olx.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * OlxParser
 *
 * @author Yurii Salimov
 * @since 1.0
 */
final class DocumentOfferParser implements Parser<Collection<Offer>> {

    private final Parser<Document> documentParser;
    private final ParseSelectors parseSelectors;
    private final boolean withPromotedOffers;

    DocumentOfferParser(
            final Parser<Document> documentParser,
            final ParseSelectors parseSelectors,
            final boolean withPromotedOffers
    ) {
        this.documentParser = documentParser;
        this.parseSelectors = parseSelectors;
        this.withPromotedOffers = withPromotedOffers;
    }

    @Override
    public Collection<Offer> parse() {
        return this.documentParser.parse()
                .select(rootCssQuery())
                .stream()
                .map(this::parse)
                .collect(Collectors.toList());
    }

    private String rootCssQuery() {
        return this.withPromotedOffers ?
                this.parseSelectors.getOfferSelector() :
                this.parseSelectors.getNotPromotedOfferSelector();
    }

    private Offer parse(final Element headline) {
        return new Offer(
                parseTitle(headline),
                parseUrl(headline),
                parseImgSrc(headline),
                parseLocation(headline),
                parsePrice(headline)
        );
    }

    private String parseTitle(final Element headline) {
        return headline.getElementsByClass(this.parseSelectors.getLinkSelector())
                .text();
    }

    private String parseUrl(final Element headline) {
        return headline.getElementsByClass(this.parseSelectors.getLinkSelector())
                .attr("href");
    }

    private String parseImgSrc(final Element headline) {
        return headline.getElementsByTag(this.parseSelectors.getImgSelector())
                .attr("src");
    }

    private String parseLocation(final Element headline) {
        return headline.select(this.parseSelectors.getLocationSelector())
                .text();
    }

    private String parsePrice(final Element headline) {
        return headline.getElementsByClass(this.parseSelectors.getPriceSelector())
                .text();
    }
}
