package ippobet.controller;

import com.ippobet.controller.BetController;
import com.ippobet.repository.BetRepository;
import org.junit.jupiter.api.Test;

public class BetControllerTest {
    @Test
    void testIfEventControllerIsInstantiable(){
        var repository = new BetRepository();
        var controller = new BetController(repository);
    }

    @Test
    void testGetAllEvents() {
        var repository = new BetRepository();
        var controller = new BetController(repository);
    }
}
