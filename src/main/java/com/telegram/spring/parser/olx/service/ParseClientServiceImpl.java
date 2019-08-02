/*
 * Created by Yurii Salimov, 02.08.2019
 * Copyright (c) 2019 Yurii Salimov
 */
package com.telegram.spring.parser.olx.service;

import com.telegram.spring.parser.olx.dao.ParseClientRepository;
import com.telegram.spring.parser.olx.parser.ParseClient;
import com.telegram.spring.parser.olx.util.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ParseClientServiceImpl
 *
 * @author Yurii Salimov
 * @since 1.0
 */
@Service
public final class ParseClientServiceImpl implements ClientService {

    private final ParseClientRepository clientRepository;
    private final ClientBuilderService clientBuilderService;

    @Autowired
    public ParseClientServiceImpl(
            final ParseClientRepository clientRepository,
            final ClientBuilderService clientBuilderService
    ) {
        this.clientRepository = clientRepository;
        this.clientBuilderService = clientBuilderService;
    }

    @Override
    public ParseClient<?> createNew(final Long id)
            throws IllegalArgumentException {
        check(id);
        final ParseClient<?> newClient = this.clientBuilderService.apply(id);
        this.clientRepository.add(newClient);
        return newClient;
    }

    @Override
    public ParseClient<?> getById(final Long id)
            throws IllegalArgumentException {
        check(id);
        ParseClient<?> client = this.clientRepository.getById(id);
        if (ObjectValidator.isNull(client)) {
            client = createNew(id);
        }
        return client;
    }

    @Override
    public boolean isExistById(final Long id)
            throws IllegalArgumentException {
        check(id);
        return this.clientRepository.isExistById(id);
    }

    @Override
    public boolean isExist(final ParseClient<?> client)
            throws IllegalArgumentException {
        check(client);
        return isExistById(client.getId());
    }

    @Override
    public ParseClient<?> deleteById(final Long id)
            throws IllegalArgumentException {
        check(id);
        return this.clientRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        this.clientRepository.deleteAll();
    }

    /**
     * Checks the given id value.
     *
     * @param id the id to validate
     * @throws IllegalArgumentException if the input id is null
     */
    private void check(final Long id) throws IllegalArgumentException {
        if (ObjectValidator.isNull(id)) {
            throw new IllegalArgumentException(
                    "Input id must be not null!"
            );
        }
    }

    /**
     * Checks the given instance value.
     *
     * @param instance the instance to validate
     * @throws IllegalArgumentException if the input instance is null
     */
    private void check(final ParseClient<?> instance) throws IllegalArgumentException {
        if (ObjectValidator.isNull(instance)) {
            throw new IllegalArgumentException(
                    "Input client instance must be not null!"
            );
        }
    }
}
