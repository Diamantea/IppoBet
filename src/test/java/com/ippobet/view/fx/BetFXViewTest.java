package com.ippobet.view.fx;

import com.ippobet.model.Bet;
import com.ippobet.view.fx.BetFXViewBuilder;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BetFXViewTest extends ApplicationTest
{
    private BetFXViewBuilder view;
    private TableView<Bet> table;

    @BeforeEach
    void setUp() {
        table = new TableView<>();
        table.setEditable(true);
        view = new BetFXViewBuilder(table);
        view.build(); // builds UI components, including table
    }

    @Test
    void testUpdateBetsUpdatesTable() throws Exception {
        Bet bet1 = new Bet("Team A", "Team B", "1", 2.5);
        Bet bet2 = new Bet("Team C", "Team D", "X", 3.1);
        List<Bet> bets = List.of(bet1, bet2);

        Platform.runLater(() -> view.updateBets(bets));
        // Wait for JavaFX thread to process the update
        Thread.sleep(300);

        Platform.runLater(() -> {
            assertEquals(2, table.getItems().size());
            assertEquals(bet1, table.getItems().get(0));
            assertEquals(bet2, table.getItems().get(1));
        });
    }
}

