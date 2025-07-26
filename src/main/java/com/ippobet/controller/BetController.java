package com.ippobet.controller;

import com.ippobet.repository.BetRepository;
import com.ippobet.view.BetView;
import javafx.stage.Stage;

public class BetController {
    private final BetRepository betRepository;
    private final BetView betView;

    public BetController(BetRepository repository, BetView view) {
        this.betRepository = repository;
        this.betView = view;
    }

    public void run(Stage primaryStage) {
        betView.updateBets(betRepository.findAllBets());
        primaryStage.show();
    }

    public void getAllEvents() {
        var bets = betRepository.findAllBets();

        betView.updateBets(bets);
    }
}
