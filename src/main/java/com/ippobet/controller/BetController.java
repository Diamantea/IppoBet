package com.ippobet.controller;

import com.ippobet.repository.BetRepository;
import com.ippobet.view.BetView;

public class BetController {
    private final BetRepository betRepository;
    private final BetView betView;

    public BetController(BetRepository repository, BetView view) {
        this.betRepository = repository;
        this.betView = view;
    }

    public void getAllEvents() {
        var bets = betRepository.findAllBets();

        betView.showAllBets(bets);
    }
}
