package com.ippobet.repository.mongo;

import com.ippobet.model.Bet;
import com.ippobet.repository.BetRepository;
import java.util.List;

public class BetMongoRepository implements BetRepository
{
    @Override
    public List<Bet> findAllBets()
    {
        return List.of();
    }
}
