package com.ippobet;

import com.ippobet.controller.BetController;
import com.ippobet.model.Bet;
import com.ippobet.repository.mongo.BetMongoRepository;
import com.ippobet.view.fx.BetFXViewBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class App extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        TableView<Bet> table = new TableView<>();
        table.setEditable(true);
        BetFXViewBuilder view = new BetFXViewBuilder(table);
        BetController controller = new BetController(new BetMongoRepository(), view);
        Scene scene = new Scene(view.build());
        primaryStage.setScene(scene);

        controller.run(primaryStage);
    }
}
