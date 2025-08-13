package com.ippobet.repository.mongo;

import com.ippobet.model.Bet;
import com.ippobet.repository.BetRepository;
import com.mongodb.client.MongoCollection;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.StreamSupport;
import org.bson.Document;

public class BetMongoRepository implements BetRepository
{
    public static final String HOME_TEAM_ATTR = "home_team";
    public static final String AWAY_TEAM_ATTR = "away_team";
    public static final String OUTCOME_ATTR = "outcome";
    public static final String ODD_ATTR = "odd";

    private final MongoCollection<Document> betCollection;


    public BetMongoRepository(MongoCollection<Document> betCollection)
    {
        this.betCollection = betCollection;
    }


    @Override
    public List<Bet> findAllBets()
    {
        return toBet(betCollection.find().spliterator());
    }

    public static List<Bet> toBet(Spliterator<Document> iterator) {
        return StreamSupport.stream(iterator, false)
            .map(BetMongoRepository::toBet)
            .toList();
    }

    public static Bet toBet(Document doc)
    {
        return new Bet(doc.getString(HOME_TEAM_ATTR), doc.getString(AWAY_TEAM_ATTR),
            doc.getString(OUTCOME_ATTR), doc.getInteger(ODD_ATTR));
    }
}
