package com.ippobet.controller;

import com.ippobet.model.Bet;
import com.ippobet.repository.BetRepository;
import com.ippobet.view.BetView;
import javafx.stage.Stage;

public class BetController
{
    private final BetRepository betRepository;
    private final BetView view;


    public BetController(BetRepository repository, BetView view)
    {
        this.betRepository = repository;
        this.view = view;
    }


    public void run(Stage primaryStage)
    {
        primaryStage.show();
    }


    public void showAllBets()
    {
        view.updateBets(betRepository.findAllBets());
    }


    public void addBet(Bet newBet)
    {
        betRepository.save(newBet);
        view.updateBets(betRepository.findAllBets());
    }


    public void deleteBet(Bet bet)
    {
        betRepository.delete(bet);
        view.updateBets(betRepository.findAllBets());
    }
}
