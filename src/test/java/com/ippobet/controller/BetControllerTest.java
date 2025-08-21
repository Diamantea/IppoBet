package com.ippobet.controller;

import com.ippobet.model.Bet;
import com.ippobet.repository.BetRepository;
import com.ippobet.view.BetView;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BetControllerTest {
    @Test
    void testGetAllEvents() {
        var bets = List.of(new Bet("home", "away", "X", 1.2));
        var repository = Mockito.mock(BetRepository.class);
        var controller = new BetController(repository);
        Mockito.when(repository.findAllBets())
                .thenReturn(bets);

        var result = controller.getAllBets();

        Assertions.assertEquals(bets, result);
    }

    @Test
    void testAddNewBet() {
        var bet = new Bet("home", "away", "X", 1.2);
        var repository = Mockito.mock(BetRepository.class);
        var controller = new BetController(repository);

        controller.addBet(bet);

        Mockito.verify(repository).save(bet);
    }
}
