package com.ippobet.controller;

import com.ippobet.model.Bet;
import com.ippobet.repository.BetRepository;
import java.util.List;
import javafx.stage.Stage;

public class BetController {
    private final BetRepository betRepository;

    public BetController(BetRepository repository) {
        this.betRepository = repository;
    }

    public void run(Stage primaryStage) {
        primaryStage.show();
    }

    public List<Bet> getAllBets() {
        return betRepository.findAllBets();
    }
}
