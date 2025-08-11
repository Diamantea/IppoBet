package com.ippobet.repository.mongo;

import com.ippobet.repository.mongo.BetMongoRepository;
import com.mongodb.client.MongoCollection;
import io.cucumber.java.Before;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class BetMongoRepositoryTest
{
    @Mock
    MongoCollection<Document> betCollection;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIfRepositoryIsInstantiable() {
        var repository = new BetMongoRepository(betCollection);
    }
}
