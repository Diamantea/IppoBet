package com.ippobet.repository;

import com.ippobet.model.Bet;
import java.util.List;

public interface BetRepository
{
    List<Bet> findAllBets();

    void save(Bet newBet);

    void delete(Bet bet);
}
