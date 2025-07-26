package ippobet.controller;

import com.ippobet.controller.BetController;
import com.ippobet.model.Bet;
import com.ippobet.repository.BetRepository;
import com.ippobet.view.BetView;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class BetControllerTest {
    @Test
    void testGetAllEvents() {
        var bets = List.of(new Bet("home", "away", "X", 1.2));
        var repository = Mockito.mock(BetRepository.class);
        var view = Mockito.mock(BetView.class);
        var controller = new BetController(repository, view);
        Mockito.when(repository.findAllBets())
                .thenReturn(bets);

        controller.getAllEvents();

        Mockito.verify(view).updateBets(bets);
    }
}
