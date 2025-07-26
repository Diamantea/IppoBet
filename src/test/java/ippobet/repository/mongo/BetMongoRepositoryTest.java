package ippobet.repository.mongo;

import com.ippobet.repository.mongo.BetMongoRepository;
import org.junit.jupiter.api.Test;

public class BetMongoRepositoryTest
{
    @Test
    void testIfRepositoryIsInstantiable() {
        var repository = new BetMongoRepository();
    }
}
