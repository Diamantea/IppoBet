package com.ippobet.controller;

import com.ippobet.model.Bet;
import com.ippobet.repository.BetRepository;
import com.ippobet.view.BetView;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BetControllerTest
{
    private BetController controller;
    private BetView view;
    private BetRepository repository;


    @BeforeEach
    public void setUp()
    {
        this.repository = Mockito.mock(BetRepository.class);
        this.view = Mockito.mock(BetView.class);
        this.controller = new BetController(repository, view);
    }


    @Test
    void testGetAllEvents()
    {
        var bets = List.of(new Bet("home", "away", "X", 1.2));
        Mockito.when(repository.findAllBets())
            .thenReturn(bets);

        controller.showAllBets();

        Mockito.verify(view).updateBets(bets);
    }


    @Test
    void testAddNewBet()
    {
        var bet = new Bet("home", "away", "X", 1.2);
        Mockito.when(repository.findAllBets())
            .thenReturn(List.of(bet));

        controller.addBet(bet);

        Mockito.verify(repository).save(bet);
        Mockito.verify(repository).findAllBets();
    }
}
