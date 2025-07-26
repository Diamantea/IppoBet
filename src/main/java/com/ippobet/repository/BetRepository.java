package com.ippobet.repository;

import com.ippobet.model.Bet;

import java.util.List;

public interface BetRepository {
    List<Bet> findAllBets();
}
