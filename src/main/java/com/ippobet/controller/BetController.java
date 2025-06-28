package com.ippobet.controller;

import com.ippobet.repository.BetRepository;

public class BetController {
    private final BetRepository betRepository;

    public BetController(BetRepository repository) {
        this.betRepository = repository;
    }
}
